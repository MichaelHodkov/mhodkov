package ru.kk.service;

import ru.kk.model.UrlTable;

public interface UrlService {
    UrlTable add(final  UrlTable urlTable);
    UrlTable getByShortUrl(final String shortUrl);
    UrlTable getByUrl(final String url);
}