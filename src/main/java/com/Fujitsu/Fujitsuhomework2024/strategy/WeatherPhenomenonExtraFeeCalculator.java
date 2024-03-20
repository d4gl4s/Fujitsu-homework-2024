package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.Weather;

public class WeatherPhenomenonExtraFeeCalculator implements ExtraFeeCalculator {
    @Override
    public double calculateExtraFee(Weather weather, String vehicleType) {
        String weatherPhenomenon = weather.getWeatherPhenomenon();
        if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && ("Snow".equalsIgnoreCase(weatherPhenomenon) || "Sleet".equalsIgnoreCase(weatherPhenomenon))) {
            return 1.0; // Extra fee based on snow or sleet weather phenomenon
        } else if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && "Rain".equalsIgnoreCase(weatherPhenomenon)) {
            return 0.5; // Extra fee based on rain weather phenomenon
        } else if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && ("Glaze".equalsIgnoreCase(weatherPhenomenon) || "Hail".equalsIgnoreCase(weatherPhenomenon) || "Thunder".equalsIgnoreCase(weatherPhenomenon))) {
            throw new IllegalArgumentException("Usage of selected vehicle type is forbidden: Weather phenomenon is not suitable");
        }
        return 0.0;
    }
}

