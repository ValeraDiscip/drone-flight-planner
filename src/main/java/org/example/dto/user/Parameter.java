package org.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parameter {
    private String language;
    private String location;
    private Short minTemperature;
    private Short maxTemperature;
    private Short maxWindSpeed;
    private Short maxWindGust;
    private Short maxHumidity;
    private Short maxPressure;
}
