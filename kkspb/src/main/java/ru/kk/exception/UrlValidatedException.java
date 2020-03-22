package ru.kk.exception;

import lombok.Getter;

@Getter
public class UrlValidatedException extends BusinessException {
    private String url;

    public UrlValidatedException(String format, String url) {
        super(format, url);
        this.url = url;
    }

}
