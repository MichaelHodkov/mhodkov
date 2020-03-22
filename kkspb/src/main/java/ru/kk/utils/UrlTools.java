package ru.kk.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.kk.domain.UrlTable;
import ru.kk.exception.UrlValidatedException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static ru.kk.constants.ErrorMessages.BAD_FULL_URL;
import static ru.kk.constants.ErrorMessages.SHORT_URL_NOT_VALID;

@Slf4j
@Component
@PropertySource("classpath:config.properties")
public final class UrlTools {
    private long urlLifeTimeLong = 0;

    private String domainName;
    private int urlLifeTime;
    private int urlOffset;

    @Value("${domain.name}")
    public void setDomainName(String domainName) {
        this.domainName = domainName.toLowerCase();
    }

    @Value("${url.lifeTime}")
    public void setUrlLifeTime(int urlLifeTime) {
        this.urlLifeTime = urlLifeTime;
        urlLifeTimeLong = urlLifeTime * 1000 * 60;
    }

    @Value("${url.offset}")
    public void setUrlOffset(int urlOffset) {
        this.urlOffset = urlOffset;
    }

    /**
     * Генерация короткой сыылки из id ссылки
     *
     * @param id ид {@link UrlTable}
     * @return короткая ссылка
     */
    public String generateShortUrl(@NotNull Long id) {
        return Long.toHexString(urlOffset + id);
    }

    /**
     * Получить короткую ссылку без доменного имени
     *
     * @param shortUrl короткая ссылка
     * @return короткая ссылка без доменного имени
     */
    public String getShortUrlWithoutDomain(@NotBlank String shortUrl) {
        if (shortUrl.toLowerCase().startsWith(domainName)) {
            shortUrl = shortUrl.replace(domainName.concat("/"), "");
        }

        return shortUrl;
    }

    /**
     * Проверка что ссылка начинается на http:// либо https://
     *
     * @param fullUrl полная ссылка
     */
    public void isUrlStartWithHttp(@NotBlank String fullUrl) {
        if (!fullUrl.matches("^https?:\\/\\/.+$")) {
            UrlValidatedException exception = new UrlValidatedException(BAD_FULL_URL, fullUrl);
            log.error(exception.getUrl());
            throw exception;
        }
    }

    /**
     * Добавить доменное имя ресурса перед короткой ссылкой
     *
     * @param shortUrl короткая ссылка
     * @return короткая ссылка с доменным именем
     */
    public String addDomain(@NonNull String shortUrl) {
        return domainName.concat("/").concat(shortUrl);
    }

    /**
     * Проверка времени ссылки на валидность
     *
     * @param urlTable {@link UrlTable}
     */
    public void validTime(UrlTable urlTable) {
        if (getUTCTimeLong() > (urlTable.getTime().getTime() + urlLifeTimeLong)) {
            UrlValidatedException exception =
                    new UrlValidatedException(SHORT_URL_NOT_VALID, addDomain(urlTable.getShortUrl()));
            log.error(exception.getUrl());
            throw exception;
        }
    }

    /**
     * Получить текущее время в виде {@link Long} без часового пояса (UTC)
     *
     * @return время в виде {@link Long}
     */
    private Long getUTCTimeLong() {
        return getUTCTimestamp().getTime();
    }

    /**
     * Получить текущее время в виде {@link Timestamp} без часового пояса (UTC)
     *
     * @return время в виде {@link Timestamp}
     */
    public Timestamp getUTCTimestamp() {
        return  Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
    }

}
