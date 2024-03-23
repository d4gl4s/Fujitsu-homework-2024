package com.Fujitsu.Fujitsuhomework2024.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
@JacksonXmlRootElement(localName = "observations")
public class Observations {
    @JacksonXmlProperty(isAttribute = true)
    private long timestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private List<WeatherObservation> stations;

}
