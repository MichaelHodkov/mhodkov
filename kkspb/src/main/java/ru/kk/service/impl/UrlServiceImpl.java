package ru.kk.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.kk.domain.UrlTable;
import ru.kk.exception.ShortUrlNotFoundException;
import ru.kk.repository.UrlRepository;
import ru.kk.service.interfaces.UrlService;
import ru.kk.utils.UrlTools;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

import static ru.kk.constants.ErrorMessages.SHORT_URL_NOT_FOUND;
import static ru.kk.constants.InfoMessages.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated
public class UrlServiceImpl implements UrlService {
    private final UrlRepository repository;
    private final UrlTools urlTools;

    @Transactional
    @Override
    public String getOrCreateShortUrl(@NotBlank String fullUrl) {
        log.debug(GET_OR_CREATE_SHORT_URL, fullUrl);

        urlTools.isUrlStartWithHttp(fullUrl);

        Optional<UrlTable> urlTableOpt = repository.findByUrl(fullUrl);

        UrlTable urlTable;

        if (urlTableOpt.isPresent()) {
            urlTable = updateUrl(urlTableOpt.get());
        } else {
            urlTable = createUrlTable(fullUrl);
        }

        String shortUrl = urlTable.getShortUrl();

        log.debug(GOT_SHORT_URL, shortUrl);

        return shortUrl;
    }

    @Override
    public String getOrCreateShortUrlWithDomain(@NotBlank String fullUrl) {
        return urlTools.addDomain(getOrCreateShortUrl(fullUrl));
    }

    /**
     * Обновить время жизни ссылки
     *
     * @param urlTable {@link UrlTable}
     * @return {@link UrlTable}
     */
    private UrlTable updateUrl(@NonNull UrlTable urlTable) {
        log.debug(UPDATE_URLTABLE, urlTable);

        urlTable.setTime(urlTools.getUTCTimestamp());

        urlTable = repository.save(urlTable);

        log.debug(UPDATED_URLTABLE, urlTable);

        return urlTable;
    }

    /**
     * Создать {@link UrlTable}
     *
     * @param fullUrl полная ссылка
     * @return {@link UrlTable}
     */
    private UrlTable createUrlTable(@NonNull String fullUrl) {
        log.debug(CREATE_URLTABLE, fullUrl);

        UrlTable urlTable = new UrlTable();
        urlTable.setUrl(fullUrl);
        urlTable.setTime(urlTools.getUTCTimestamp());

        urlTable = repository.save(urlTable);

        urlTable = createShortUrl(urlTable);

        log.debug(CREATED_URLTABLE, urlTable);

        return urlTable;
    }

    /**
     * Создать короткую ссылку для {@link UrlTable}
     *
     * @param urlTable {@link UrlTable}
     * @return {@link UrlTable}
     */
    private UrlTable createShortUrl(@NonNull UrlTable urlTable) {
        log.debug(CREATE_SHORT_URL, urlTable);

        urlTable.setShortUrl(urlTools.generateShortUrl(urlTable.getId()));
        urlTable = repository.save(urlTable);

        log.debug(CREATED_SHORT_URL, urlTable);

        return urlTable;
    }

    @Transactional(readOnly = true)
    @Override
    public String getFullUrlByShortUrl(@NotBlank String shortUrl) {
        log.debug(GET_FULL_URL_BY_SHORT_URL, shortUrl);

        UrlTable urlTable = getUrlTableByShortUrl(shortUrl);

        log.debug(GOT_FULL_URL, urlTable.getUrl());

        return urlTable.getUrl();
    }

    @Override
    public String getFullUrlByShortUrlWithValidTime(@NotBlank String shortUrl) {
        log.debug(GET_FULL_URL_BY_SHORT_URL, shortUrl);

        UrlTable urlTable = getUrlTableByShortUrl(shortUrl);

        urlTools.validTime(urlTable);

        log.debug(GOT_FULL_URL, urlTable.getUrl());

        return urlTable.getUrl();
    }

    /**
     * Получить {@link UrlTable} по короткой ссылку
     *
     * @param shortUrl короткая ссылка
     * @return {@link UrlTable}
     */
    private UrlTable getUrlTableByShortUrl(@NotBlank String shortUrl) {
        log.debug(GET_URLTABLE_BY_SHORT_URL, shortUrl);

        String shortUrlWithoutDomain = urlTools.getShortUrlWithoutDomain(shortUrl);

        Optional<UrlTable> urlTableOpt = repository.findByShortUrl(shortUrlWithoutDomain);

        if (!urlTableOpt.isPresent()) {
            ShortUrlNotFoundException exception =
                    new ShortUrlNotFoundException(SHORT_URL_NOT_FOUND, shortUrlWithoutDomain);
            log.error(exception.getUrl());
            throw exception;
        }

        log.debug(GOT_URLTABLE, urlTableOpt.get());

        return urlTableOpt.get();
    }

}
