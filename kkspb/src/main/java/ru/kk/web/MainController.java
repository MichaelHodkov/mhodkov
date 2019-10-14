package ru.kk.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kk.model.UrlTable;
import ru.kk.service.UrlService;
import ru.kk.utils.UrlTools;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(MainController.class);
    private final UrlService urlService;
    private final UrlTools urlTools;

    @Autowired
    public MainController(UrlService urlService, UrlTools urlTools) {
        LOG.info("Загрузка контроллера 'MainController'.");
        this.urlService = urlService;
        this.urlTools = urlTools;
    }

    @GetMapping("/*")
    public String interceptionAllGetMapping(
            @RequestParam(value = "fullUrl", required = false) String url,
            ModelMap model,
            HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        LOG.info(String.format("Перехват, запрос: '%s'", requestUrl));
        if (!requestUrl.equals("/") && !requestUrl.equals("/main")) {
            LOG.info("Поиск и редирект по короткому адресу");
            String shortUrl = requestUrl.replace("/", "");
            UrlTable urlTable = urlService.getByShortUrl(shortUrl);
            if (urlTable != null) {
                LOG.info(String.format("Найдена запись в БД: %s", urlTable));
                if (urlTools.validTime(urlTable.getTime())) {
                    LOG.info(String.format("Редирект по ссылке: '%s' ", urlTable.getUrl()));
                    return "redirect:".concat(urlTable.getUrl());
                } else {
                    LOG.error(String.format("URL (%s) просрочен: %s", shortUrl, urlTable.getTime()));
                    model.addAttribute("error",
                            String.format("URL (%s) не действителен, время жизни (%d мин.). Обновите URL (%s)",
                                    urlTools.domainName.concat(requestUrl), urlTools.urlLifeTime, urlTable.getUrl()));
                    model.addAttribute("fullUrl", urlTable.getUrl());
                }
            } else {
                LOG.error(String.format("URL (%s) не найден", shortUrl));
                model.addAttribute("error", String.format("URL (%s) не найден", shortUrl));
            }
        } else {
            LOG.info("Загрузка главной страницы");
            model.addAttribute("fullUrl", url);
        }
        return "main";
    }

    @PostMapping("/main")
    public String createOrUpdateShortUrl(@RequestParam(value = "url", defaultValue = "") String url, ModelMap model) {
        LOG.info(String.format("Запрос на получения нового короткого URL из: '%s'", url));
        if (!url.equals("")) {
            LOG.info(String.format("Проверка URL (%s) на корректность", url));
            if (urlTools.isCorrectUrl(url)) {
                LOG.info(String.format("Поиск URL (%s) в БД", url));
                UrlTable urlTable = urlService.getByUrl(url);
                if (urlTable != null) {
                    updateUrl(urlTable);
                } else {
                    urlTable = createShortUrl(url);
                }
                model.addAttribute("shortUrl", urlTools.addDomain(urlTable.getShortUrl()));
                return "short";
            } else {
                LOG.error(String.format("URL (%s) не валидный", url));
                model.addAttribute("fullUrl", url);
                model.addAttribute("error", "URL не прошел валидацию");
            }
        } else {
            LOG.error("URL пуст");
            model.addAttribute("error", "URL не может быть пустым");
        }
        return "main";
    }

    private UrlTable createShortUrl(String url) {
        LOG.info(String.format("Генерируем короткий URL из (%s)", url));
        UrlTable urlTable = urlService.add(new UrlTable(url, urlTools.getUtcTimestamp()));
        urlTable.setShortUrl(urlTools.generateShortUrl(urlTable.getId()));
        LOG.info(String.format("Сгенерирован короткий URL (%s)", urlTools.addDomain(urlTable.getShortUrl())));
        urlTable = urlService.add(urlTable);
        LOG.info(String.format("Запись в БД (%s)", urlTable));
        return urlTable;
    }

    private void updateUrl(UrlTable urlTable) {
        LOG.info(String.format("Найдена запись в БД: %s", urlTable));
        LOG.info("Обновление времени");
        urlTable.setTime(urlTools.getUtcTimestamp());
        urlTable = urlService.add(urlTable);
        LOG.info("Время обновленно");
        LOG.info(String.format("Звпись в БД: %s", urlTable));
    }
}