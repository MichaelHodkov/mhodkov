package ru.kk.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kk.model.JsonUrl;
import ru.kk.model.UrlTable;
import ru.kk.service.UrlService;
import ru.kk.utils.UrlTools;

import java.io.UnsupportedEncodingException;

@Controller
public class FullUrlController {
    private static final Logger LOG = Logger.getLogger(FullUrlController.class);
    private final UrlService urlService;
    private final UrlTools urlTools;

    @Autowired
    public FullUrlController(UrlService urlService, UrlTools urlTools) {
        LOG.info("Загрузка контроллера 'FullUrlController'");
        this.urlService = urlService;
        this.urlTools = urlTools;
    }

    @GetMapping("/fullUrl")
    public String showFullPage(@RequestParam(value = "url", defaultValue = "") String url, ModelMap model) {
        LOG.info("Загрузка страницы для получения полного URL из короткого");
        model.addAttribute("url", url);
        LOG.info(String.format("Короткая ссылка: %s", url));
        return "full";
    }

    @PostMapping("/fullUrl")
    public @ResponseBody JsonUrl getFullUrl(@RequestBody JsonUrl jsonUrl) {
        jsonUrl.decode();
        LOG.info(String.format("Запрос на получения полного URL (%s)", jsonUrl.url));
        if (urlTools.isCorrectShortUrl(jsonUrl)) {
            String shortUrl = urlTools.getShortUrl(jsonUrl.url);
            LOG.info(String.format("Поиск URL (%s) в БД", shortUrl));
            UrlTable urlTable = urlService.getByShortUrl(shortUrl);
            if (urlTable != null) {
                LOG.info(String.format("Запись в БД %s", urlTable));
                jsonUrl.url = urlTable.getUrl();
                jsonUrl.status = UrlTools.FIND;
                jsonUrl.encode();
                return jsonUrl;
            } else {
                jsonUrl.status = UrlTools.ERROR;
                jsonUrl.error = String.format("URL (%s) не найден в БД", jsonUrl.url);
            }
        }
        LOG.error(jsonUrl.error);
        jsonUrl.encode();
        return jsonUrl;
    }
}