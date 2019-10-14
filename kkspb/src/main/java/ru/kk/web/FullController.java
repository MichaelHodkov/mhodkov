package ru.kk.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kk.model.UrlTable;
import ru.kk.service.UrlService;
import ru.kk.utils.UrlTools;

@Controller
public class FullController {
    private static final Logger LOG = Logger.getLogger(FullController.class);
    private final UrlService urlService;
    private final UrlTools urlTools;

    @Autowired
    public FullController(UrlService urlService, UrlTools urlTools) {
        LOG.info("Загрузка контроллера 'FullController'.");
        this.urlService = urlService;
        this.urlTools = urlTools;
    }

    @GetMapping("/full")
    public String showFullPage(
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "error", required = false) String error,
            ModelMap model) {
        LOG.info("Загрузка страницы для получения полного URL из короткого.");
        model.addAttribute("url", url);
        model.addAttribute("error", error);
        return "full";
    }

    @PostMapping("/full")
    public ModelAndView getUrl(@RequestParam("url") String url, ModelMap model) {
        LOG.info("Запрос на получения полного URL");
        if (!url.equals("")) {
            LOG.info(String.format("Проверяем URL (%s)", url));
            if (urlTools.isCorrectShortUrl(url)) {
                String shortUrl = urlTools.getShortUrl(url);
                LOG.info(String.format("Поиск URL (%s) в БД", shortUrl));
                UrlTable urlTable = urlService.getByShortUrl(shortUrl);
                if (urlTable != null) {
                    LOG.info(String.format("Запись в БД %s", urlTable));
                    model.addAttribute("fullUrl", urlTable.getUrl());
                    return new ModelAndView("redirect:main", model);
                } else {
                    LOG.error(String.format("URL (%s) не найден в БД", shortUrl));
                    model.addAttribute("error", "URL не найден");
                }
            } else {
                LOG.error(String.format("URL (%s) не валидный", url));
                model.addAttribute("error", "URL не прошел валидацию");
            }
        } else {
            LOG.error("URL пуст");
            model.addAttribute("error", "URL не может быть пустым");
        }
        model.addAttribute("url", url);
        return new ModelAndView("redirect:full", model);
    }
}