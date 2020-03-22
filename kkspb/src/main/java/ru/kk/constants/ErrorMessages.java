package ru.kk.constants;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String UNKNOWN_RUNTIME_EXCEPTION = "[SHORT-URL-E00] Произошла неизвестная ошибка: '%s'";
    public static final String BAD_REQUEST_ERROR = "[SHORT-URL-E01] Запрос не прошел валидацию. %s";
    public static final String BAD_FULL_URL =
            "[SHORT-URL-E02] Ссылка '%s' должна начинаться с 'http://' либо 'https://'";
    public static final String SHORT_URL_NOT_FOUND = "[SHORT-URL-E03] Короткая ссылка '%s' не найдена";
    public static final String SHORT_URL_NOT_VALID = "[SHORT-URL-E04] Короткая ссылка '%s' больше не валидная";

}
