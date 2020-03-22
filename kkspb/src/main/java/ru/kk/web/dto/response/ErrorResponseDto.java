package ru.kk.web.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.kk.web.dto.DtoModel;
import ru.kk.web.dto.ErrorType;

@ApiModel(description = "Json с ошибкой")
@Data
@AllArgsConstructor
public class ErrorResponseDto implements DtoModel {

    @ApiModelProperty(name = "message", value = "Сообщение об ошибке", dataType = "String")
    private String message;

    @ApiModelProperty(name = "cause", value = "Причина ошибки", dataType = "String")
    private String cause;

    @ApiModelProperty(name = "type", value = "Тип ошибки", dataType = "ErrorType")
    private ErrorType type;

}
