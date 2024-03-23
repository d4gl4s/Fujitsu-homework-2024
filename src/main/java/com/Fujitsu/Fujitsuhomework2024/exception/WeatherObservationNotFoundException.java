package com.Fujitsu.Fujitsuhomework2024.exception;

public class WeatherObservationNotFoundException extends RuntimeException {

    public WeatherObservationNotFoundException() {
        super("Weather observation not found");
    }

    public WeatherObservationNotFoundException(String message) {
        super(message);
    }

}
