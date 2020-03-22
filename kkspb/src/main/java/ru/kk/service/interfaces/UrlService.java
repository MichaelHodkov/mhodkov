package ru.kk.service.interfaces;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public interface UrlService {

    /**
     * Получить или добавить новую короткую ссылку
     *
     * @param url ссылка
     * @return короткая ссылка
     */
    String getOrCreateShortUrl(@NotBlank final String url);

    /**
     * Получить или добавить новую короткую ссылку с доменным именем
     *
     * @param url ссылка
     * @return короткая ссылка с доменным именем
     */
    String getOrCreateShortUrlWithDomain(@NotBlank final String url);

    /**
     * Получить полную ссылку по короткой ссылке
     *
     * @param shortUrl короткая ссылка
     * @return полная ссылка
     */
    String getFullUrlByShortUrl(@NotBlank final String shortUrl);

    /**
     * Получить полную ссылку по короткой ссылке с валидацией времени
     *
     * @param shortUrl короткая ссылка
     * @return полная ссылка
     */
    String getFullUrlByShortUrlWithValidTime(@NotBlank final String shortUrl);

}
