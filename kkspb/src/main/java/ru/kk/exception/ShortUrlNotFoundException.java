package ru.kk.exception;

import lombok.Getter;

@Getter
public class ShortUrlNotFoundException extends BusinessException {
    private String url;

    public ShortUrlNotFoundException(String format, String url) {
        super(format, url);
        this.url = url;
    }

}
