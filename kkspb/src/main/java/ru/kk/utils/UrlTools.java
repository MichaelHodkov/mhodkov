package ru.kk.utils;

import org.apache.log4j.Logger;
import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.kk.model.JsonUrl;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.*;

@Component
@PropertySource("classpath:config.properties")
public class UrlTools {
    private static final Logger LOG = Logger.getLogger(UrlTools.class);
    private long urlLifeTimeLong = 0;

    public String domainName;
    public int urlLifeTime;
    private int urlOffset;

    public static final int ERROR = -1;
    public static final int CREATED = 1;
    public static final int UPDATE = 2;
    public static final int FIND = 3;

    public UrlTools() {
    }

    @Value("${domain.name}")
    public void setDomainName(String domainName) {
        this.domainName = domainName;
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

    public String generateShortUrl(long id) {
        return Long.toHexString(urlOffset + id);
    }

    @NotNull
    public String getShortUrl(String url) {
        return url.replace(domainName.concat("/"), "");
    }

    @NotNull
    public boolean isCorrectUrl(JsonUrl jsonUrl) {
        return isNotNull(jsonUrl) && isNotEmpty(jsonUrl) && isUrlStartWithHttp(jsonUrl) &&
                isNoSpaces(jsonUrl) && isCorrectLength(jsonUrl);
    }

    @NotNull
    public boolean isCorrectShortUrl(JsonUrl jsonUrl) {
        return isNotNull(jsonUrl) && isNotEmpty(jsonUrl) && isUrlStartWithDomain(jsonUrl) &&
                isNoSpaces(jsonUrl) && isCorrectLength(jsonUrl);
    }

    @NotNull
    public boolean isNotNull(JsonUrl jsonUrl) {
        if (jsonUrl.url != null) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("Json (%s) не содержит url", jsonUrl);
        return false;
    }

    @NotNull
    private boolean isNotEmpty(JsonUrl jsonUrl) {
        if (!jsonUrl.url.isEmpty()) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("URL (%s) пуст", jsonUrl.url);
        return false;
    }

    @NotNull
    public boolean isNoSpaces(JsonUrl jsonUrl) {
        if (jsonUrl.url.matches("^\\S+$")) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("URL (%s) содержит пробел/ы", jsonUrl.url);
        return false;
    }

    @NotNull
    public boolean isUrlStartWithHttp(JsonUrl jsonUrl) {
        if (jsonUrl.url.matches("^https?:\\/\\/.+$")) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("URL (%s) должен начинать с 'http://' либо 'https://'", jsonUrl.url);
        return false;
    }

    @NotNull
    public boolean isCorrectLength(JsonUrl jsonUrl) {
        if (jsonUrl.url.length() > 10) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("URL (%s) меньше 11 символов", jsonUrl.url);
        return false;
    }

    @NotNull
    public boolean isUrlStartWithDomain(JsonUrl jsonUrl) {
        if (jsonUrl.url.toLowerCase().startsWith(domainName.toLowerCase())) {
            return true;
        }
        jsonUrl.status = ERROR;
        jsonUrl.error = String.format("Неизветный URL (%s)", jsonUrl.url);
        return false;
    }

    @NotNull
    public String addDomain(String shortUrl) {
        return domainName.concat("/").concat(shortUrl);
    }

    @NotNull
    public boolean validTime(Timestamp time) {
        return getUtcTimeLong() <= (time.getTime() + urlLifeTimeLong);
    }

    public long getUtcTimeLong() {
        return getUtcTimestamp().getTime();
    }

    public Timestamp getUtcTimestamp() {
        return  Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")));
    }

    public static String decode(String url) {
        try {
            return new String(Base64.decode(url), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("Ошибка декодирования из BASE64", e);
            return url;
        }
    }

    public static String encode(String url)  {
        try {
            return Base64.encodeBytes(url.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            LOG.error("Ошибка кодирования в BASE64", e);
            return url;
        }
    }
}