package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.Weather;
import com.Fujitsu.Fujitsuhomework2024.repository.WeatherRepository;
import com.Fujitsu.Fujitsuhomework2024.strategy.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DeliveryService {
    private final WeatherRepository weatherRepository;
    private final BaseFeeCalculator baseFeeCalculator;
    private final ExtraFeeCalc extraFeeCalculator;

    public double calculateDeliveryFee(String city, String vehicleType) {
        double baseFee = baseFeeCalculator.calculateBaseFee(city, vehicleType);
        Weather latestWeather = weatherRepository.findFirstByStationNameOrderByTimestampDesc(city);
        double extraFee = extraFeeCalculator.calculateExtraFee(latestWeather, vehicleType);
        return baseFee + extraFee;
    }
}

