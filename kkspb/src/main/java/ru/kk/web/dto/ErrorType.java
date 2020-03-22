package ru.kk.web.dto;

/**
 * Типы ошибок.
 */
public enum ErrorType {
    VALIDATION("Ошибка валидации"),
    BUSINESS("Бизнесовая ошибка"),
    SYSTEM("Системная ошибка");

    private String type;

    ErrorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
