package ru.kk.web.controllers.interfaces;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Данную аннотацию следует использовать над всеми классами контроллеров.
 * Перечень всех стандартных ошибок принятных на проекте.
 * Информация будет выводиться в swagger.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(code = 200, message = "Успех"),
        @ApiResponse(code = 400, message = "Неверный запрос"),
        @ApiResponse(code = 404, message = "Сущность не найдена"),
        @ApiResponse(code = 422, message = "Внутренняя ошибка"),
        @ApiResponse(code = 500, message = "Ошибка на сервере")
})
public @interface ApiResponsesCodes {
}
