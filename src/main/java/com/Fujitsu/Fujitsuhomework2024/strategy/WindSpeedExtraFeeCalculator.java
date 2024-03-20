package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.Weather;

public class WindSpeedExtraFeeCalculator implements ExtraFeeCalculator {
    @Override
    public double calculateExtraFee(Weather weather, String vehicleType) {
        double windSpeed = weather.getWindSpeed();
        if ("Bike".equals(vehicleType) && windSpeed >= 10 && windSpeed <= 20) {
            return 0.5; // Extra fee based on wind speed between 10 m/s and 20 m/s
        } else if ("Bike".equals(vehicleType) && windSpeed > 20) {
            throw new IllegalArgumentException("Usage of selected vehicle type is forbidden: Wind speed exceeds 20 m/s");
        }
        return 0.0;
    }
}

