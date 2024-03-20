package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.Weather;

public interface ExtraFeeCalculator {
    double calculateExtraFee(Weather weather, String vehicleType);
}
