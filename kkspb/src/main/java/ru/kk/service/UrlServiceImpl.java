package ru.kk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kk.model.UrlTable;
import ru.kk.repository.UrlRepository;

@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository repository;

    @Autowired
    public UrlServiceImpl(UrlRepository repository) {
        this.repository = repository;
    }

    @Override
    public UrlTable add(UrlTable urlTable) {
        repository.save(urlTable);
        return urlTable;
    }

    @Override
    public UrlTable getByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    @Override
    public UrlTable getByUrl(String url) {
        return repository.findByUrl(url);
    }
}