package org.example.dto.client;

import lombok.Data;

@Data
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
