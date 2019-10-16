package ru.kk.web;

import org.apache.log4j.Logger;
import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kk.model.JsonUrl;
import ru.kk.model.UrlTable;
import ru.kk.service.UrlService;
import ru.kk.utils.UrlTools;

import java.io.UnsupportedEncodingException;

@Controller
public class ShortUrlController {
    private static final Logger LOG = Logger.getLogger(ShortUrlController.class);
    private final UrlService urlService;
    private final UrlTools urlTools;

    @Autowired
    public ShortUrlController(UrlService urlService, UrlTools urlTools) {
        LOG.info("Загрузка контроллера 'ShortUrlController'");
        this.urlService = urlService;
        this.urlTools = urlTools;
    }

    @GetMapping("/shortUrl")
    public String showShortPage(@RequestParam(value = "url", defaultValue = "") String url, ModelMap model) throws UnsupportedEncodingException {
        LOG.info("Загрузка страницы с короткой ссылкой");
        url = UrlTools.decode(url);
        model.addAttribute("url", url);
        LOG.info(String.format("Короткая ссылка: %s", url));
        return "short";
    }

    @PostMapping("/shortUrl")
    public @ResponseBody JsonUrl createOrUpdateShortUrl(@RequestBody JsonUrl jsonUrl) {
        jsonUrl.decode();
        LOG.info(String.format("Запрос на получения нового короткого URL из: '%s'", jsonUrl.url));
        if (urlTools.isCorrectUrl(jsonUrl)) {
            LOG.info(String.format("Поиск URL (%s) в БД", jsonUrl.url));
            UrlTable urlTable = urlService.getByUrl(jsonUrl.url);
            if (urlTable != null) {
                updateUrl(urlTable);
                jsonUrl.status = UrlTools.UPDATE;
            } else {
                urlTable = createShortUrl(jsonUrl.url);
                jsonUrl.status = UrlTools.CREATED;
            }
            LOG.info(String.format("Звпись в БД: %s", urlTable));
            jsonUrl.url = urlTools.addDomain(urlTable.getShortUrl());
            jsonUrl.encode();
            return jsonUrl;
        }
        LOG.error(jsonUrl.error);
        jsonUrl.encode();
        return jsonUrl;
    }

    private UrlTable createShortUrl(String url) {
        LOG.info(String.format("Генерируем короткий URL из (%s)", url));
        UrlTable urlTable = urlService.add(new UrlTable(url, urlTools.getUtcTimestamp()));
        urlTable.setShortUrl(urlTools.generateShortUrl(urlTable.getId()));
        LOG.info(String.format("Сгенерирован короткий URL (%s)", urlTools.addDomain(urlTable.getShortUrl())));
        urlTable = urlService.add(urlTable);
        return urlTable;
    }

    private void updateUrl(UrlTable urlTable) {
        LOG.info(String.format("Найдена запись в БД: %s", urlTable));
        LOG.info("Обновление времени");
        urlTable.setTime(urlTools.getUtcTimestamp());
        urlTable = urlService.add(urlTable);
    }
}