package ru.kk.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "UrlTable")
@NoArgsConstructor
@AllArgsConstructor
public class UrlResponseDto {

    @ApiModelProperty(name = "url", value = "url", dataType = "String")
    @JsonProperty("url")
    private String url;

}
