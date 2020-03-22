package ru.kk.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class UrlRequestDto {

    @ApiModelProperty(name = "url", value = "полная ссылка", dataType = "String", required = true)
    @JsonProperty("url")
    @NotBlank(message = "ссылка не может быть пустой")
    @Size(min = 10, message = "Ссылка должнна быть не меньше 10 символов")
    private String url;

}
