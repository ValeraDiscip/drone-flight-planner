package org.example.dto.weather.current;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
/*
стоит указать @NoArgsConstructor? в нашем случае он и так будет так как final полей нет,
или уже по факту если появится такое поле поставить?либо сразу (лишним не будет) что посоветуете?
 */
public class Condition {
    @JsonProperty("text")
    private String weatherConditionText;

    @JsonProperty("icon")
    private String weatherIconUrl;
}
