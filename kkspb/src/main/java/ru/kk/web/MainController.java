package ru.kk.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kk.model.JsonUrl;
import ru.kk.model.UrlTable;
import ru.kk.service.UrlService;
import ru.kk.utils.UrlTools;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(MainController.class);
    private final UrlService urlService;
    private final UrlTools urlTools;

    @Autowired
    public MainController(UrlService urlService, UrlTools urlTools) {
        LOG.info("Загрузка контроллера 'MainController'");
        this.urlService = urlService;
        this.urlTools = urlTools;
    }

    @GetMapping("/*")
    public String interceptionAllGetMapping(
            @RequestParam(value = "url", required = false) String url,
            ModelMap model,
            HttpServletRequest request) throws JsonProcessingException {
        String requestUrl = request.getRequestURI();
        LOG.info(String.format("Перехват, запрос: '%s'", requestUrl));
        JsonUrl jsonUrl = new JsonUrl();
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
                    jsonUrl.error = String.format("URL (%s) не действителен, время жизни (%d мин.). Обновите URL (%s)",
                            urlTools.domainName.concat(requestUrl), urlTools.urlLifeTime, urlTable.getUrl());
                    jsonUrl.url = urlTable.getUrl();
                    jsonUrl.encode();
                }
            } else {
                jsonUrl.error = String.format("URL (%s) не найден в БД", urlTools.domainName.concat(requestUrl));
            }
            jsonUrl.status = UrlTools.ERROR;
        } else {
            LOG.info("Загрузка главной страницы");
            jsonUrl.url = url;
            jsonUrl.status = 200;
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(jsonUrl);
        model.addAttribute("json", json);
        return "main";
    }
}