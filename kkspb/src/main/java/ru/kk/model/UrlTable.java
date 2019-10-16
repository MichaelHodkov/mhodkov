package ru.kk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "url")
public class UrlTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    private String shortUrl;

    private Timestamp time;

    public UrlTable() {
    }

    public UrlTable(String url, Timestamp time) {
        this.url = url;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UrlTable{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", shortUrl='").append(shortUrl).append('\'');
        sb.append(", time=").append(time);
        sb.append(" (UTC)}");
        return sb.toString();
    }
}