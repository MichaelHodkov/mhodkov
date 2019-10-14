package ru.kk.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kk.model.UrlTable;

public interface UrlRepository extends CrudRepository<UrlTable, Integer> {
    UrlTable findByShortUrl(String shortUrl);
    UrlTable findByUrl(String url);
}