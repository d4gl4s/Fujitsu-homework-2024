package com.Fujitsu.Fujitsuhomework2024.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "station")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weather")
@JsonIgnoreProperties(value = { "longitude", "latitude", "visibility", "precipitations", "airpressure", "relativehumidity", "winddirection", "windspeedmax", "waterlevel", "waterlevel_eh2000", "uvindex", "sunshineduration", "globalradiation" }, ignoreUnknown = true)
public class WeatherObservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JacksonXmlProperty(localName = "name")
    @Column(name = "station_name")
    private String stationName;


    @JacksonXmlProperty(localName = "wmocode")
    @Column(name = "wmo_code")
    private String wmoCode;

    @JacksonXmlProperty(localName = "airtemperature")
    @Column(name = "air_temperature")
    private double airTemperature;

    @JacksonXmlProperty(localName = "windspeed")
    @Column(name = "wind_speed")
    private double windSpeed;

    @JacksonXmlProperty(localName = "phenomenon")
    @Column(name = "weather_phenomenon")
    private String weatherPhenomenon;

    public WeatherObservation(double airTemperature, double windSpeed, String weatherPhenomenon) {
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
    }

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}


