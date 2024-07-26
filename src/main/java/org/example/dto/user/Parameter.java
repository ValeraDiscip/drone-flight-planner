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
    private Double minTemperature;
    private Double maxTemperature;
    private Double maxWindSpeed;
    private Double maxWindGust;
    private Double maxHumidity;
    private Double maxPrecip;
    private Double maxPressure;
}
