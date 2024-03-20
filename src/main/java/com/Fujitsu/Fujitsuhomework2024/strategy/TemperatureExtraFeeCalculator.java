package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.Weather;

public class TemperatureExtraFeeCalculator implements ExtraFeeCalculator {
    @Override
    public double calculateExtraFee(Weather weather, String vehicleType) {
        double airTemperature = weather.getAirTemperature();
        if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && airTemperature < -10) {
            return 1.0; // Extra fee based on air temperature less than -10°C
        } else if (("Scooter".equals(vehicleType) || "Bike".equals(vehicleType)) && airTemperature >= -10 && airTemperature < 0) {
            return 0.5; // Extra fee based on air temperature between -10°C and 0°C
        }
        return 0.0;
    }
}
