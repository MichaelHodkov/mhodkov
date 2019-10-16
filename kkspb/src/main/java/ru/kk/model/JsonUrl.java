package ru.kk.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.Base64;
import ru.kk.utils.UrlTools;

import java.io.UnsupportedEncodingException;

public class JsonUrl {
    public String url;
    public int status;
    public String error;

    public JsonUrl() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void decode() {
        url = UrlTools.decode(url);
    }

    public void encode() {
        url = UrlTools.encode(url);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JsonUrl{");
        sb.append("url='").append(url).append('\'');
        sb.append(", status=").append(status);
        sb.append(", error='").append(error).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
