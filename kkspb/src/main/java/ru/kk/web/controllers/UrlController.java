package ru.kk.web.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kk.service.interfaces.UrlService;
import ru.kk.web.controllers.interfaces.ApiResponsesCodes;
import ru.kk.web.dto.request.ShortUrlRequestDto;
import ru.kk.web.dto.request.UrlRequestDto;
import ru.kk.web.dto.response.UrlResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/url")
@Api("Контроллер работы с проектами")
@Validated
@ApiResponsesCodes
public class UrlController {
    private final MapperFacade mapperFacade;
    private final UrlService urlService;

    @PostMapping("/create-short-url")
    @ApiOperation(value = "Получить короткую ссылку", httpMethod = "POST")
    public UrlResponseDto getShortUrl(@NotNull @Valid @RequestBody final UrlRequestDto requestDto) {
        String shortUrl = urlService.getOrCreateShortUrl(mapperFacade.map(requestDto, String.class));
        return mapperFacade.map(shortUrl, UrlResponseDto.class);
    }

    @PostMapping("/create-short-url-with-domain")
    @ApiOperation(value = "Получить короткую ссылку с доменным именем", httpMethod = "POST")
    public UrlResponseDto getShortUrlWithDomain(@NotNull @Valid @RequestBody final UrlRequestDto requestDto) {
        String shortUrl = urlService.getOrCreateShortUrlWithDomain(mapperFacade.map(requestDto, String.class));
        return mapperFacade.map(shortUrl, UrlResponseDto.class);
    }

    @PostMapping("/get-full-url")
    @ApiOperation(value = "Получить полную ссылку из короткой", httpMethod = "POST")
    public UrlResponseDto getFullUrl(@NotNull @Valid @RequestBody final ShortUrlRequestDto requestDto) {
        String fullUrl = urlService.getFullUrlByShortUrl(mapperFacade.map(requestDto, String.class));
        return mapperFacade.map(fullUrl, UrlResponseDto.class);
    }

    @PostMapping("/get-full-url-with-valid-time")
    @ApiOperation(value = "Получить полную ссылку из короткой с проверкой валидации времени", httpMethod = "POST")
    public UrlResponseDto getFullUrlWithValidTime(@NotNull @Valid @RequestBody final ShortUrlRequestDto requestDto) {
        String fullUrl = urlService.getFullUrlByShortUrlWithValidTime(mapperFacade.map(requestDto, String.class));
        return mapperFacade.map(fullUrl, UrlResponseDto.class);
    }

}
