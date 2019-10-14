package ru.kk.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
@PropertySource("classpath:config.properties")
public class UrlTools {
    private static final Logger LOG = Logger.getLogger(UrlTools.class);
    private long urlLifeTimeLong = 0;

    public String domainName;
    public int urlLifeTime;
    private int urlOffset;

    public UrlTools() {
    }

    @Value("${domain.name}")
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Value("${url.lifeTime}")
    public void setUrlLifeTime(int urlLifeTime) {
        this.urlLifeTime = urlLifeTime;
        this.urlLifeTimeLong = urlLifeTime * 1000 * 60;
    }

    @Value("${url.offset}")
    public void setUrlOffset(int urlOffset) {
        this.urlOffset = urlOffset;
    }

    public String generateShortUrl(long id) {
        return Long.toHexString(this.urlOffset + id);
    }

    @NotNull
    public String getShortUrl(String url) {
        return url.replace(this.domainName.concat("/"), "");
    }

    @NotNull
    public boolean isCorrectUrl(String url) {
        return isCorrectStartWithHttp(url) && isNoSpaces(url) && isCorrectLength(url);
    }

    @NotNull
    public boolean isNoSpaces(String url) {
        if (url.matches("^\\S+$")) {
            return true;
        } else {
            LOG.error(String.format("Ошибка, URL (%s) содержит пробел/ы", url));
        }
        return false;
    }

    @NotNull
    public boolean isCorrectStartWithHttp(String url) {
        if (url.matches("^https?:\\/\\/.+$")) {
            return true;
        } else {
            LOG.error(String.format("Ошибка, URL (%s) должен начинать с 'http://' либо 'https://'", url));
        }
        return false;
    }

    @NotNull
    public boolean isCorrectLength(String url) {
        if (url.length() > 10) {
            return true;
        } else {
            LOG.error(String.format("Ошибка, URL (%s) меньше 11 символов", url));
        }
        return false;
    }

    @NotNull
    public boolean isCorrectShortUrl(String url) {
        if (url.contains(this.domainName)) {
            if (url.startsWith(this.domainName)) {
                return isCorrectUrl(url);
            } else {
                LOG.error(String.format("URL (%s) не начинается с имени домена: '%s'", url, this.domainName));
            }
        } else {
            LOG.error(String.format("URL (%s) не содержит имя домена: '%s'", url, this.domainName));
        }
        return false;
    }

    @NotNull
    public String addDomain(String shortUrl) {
        return this.domainName.concat("/").concat(shortUrl);
    }

    @NotNull
    public boolean validTime(Timestamp time) {
        return getUtcTimeLong() <= (time.getTime() + this.urlLifeTimeLong);
    }

    public long getUtcTimeLong() {
        return getUtcTimestamp().getTime();
    }

    public Timestamp getUtcTimestamp() {
        return  Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
    }
}