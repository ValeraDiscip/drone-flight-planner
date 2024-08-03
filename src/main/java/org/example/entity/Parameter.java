package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parameter {
    private Integer id;
    private Integer userId;
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
