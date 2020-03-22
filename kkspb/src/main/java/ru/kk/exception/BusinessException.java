package ru.kk.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * Базовое исключение для пользовательских исключений в которых нужно сообщение прокидывать на фронт.
 */
public abstract class BusinessException extends RuntimeException {
    private final String format;
    private final Object[] args;

    public BusinessException(String format, Object... args) {
        this.format = format;
        this.args = args;
    }

    /**
     * Метод формирования сообщения из шаблона.
     *
     * @return сформированное сообщение с подсталвенными аргументами.
     */
    public String getMessage() {
        if (StringUtils.isNotBlank(format)) {
            return String.format(format, args);
        }
        return super.getMessage();
    }

}
