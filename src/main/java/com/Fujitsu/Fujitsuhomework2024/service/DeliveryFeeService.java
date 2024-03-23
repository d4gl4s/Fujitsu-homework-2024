package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryFeeService {
    private final WeatherRepository weatherRepository;
    private final BaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    public double calculateDeliveryFee(String city, String vehicleType) {
        if (city == null || vehicleType == null)
            throw new IllegalArgumentException("City and vehicle type must not be null");
        double baseFee = calculateBaseFee(city, vehicleType);
        WeatherObservation latestWeatherObservation = weatherRepository.findFirstByStationNameOrderByTimestampDesc(city);
        double extraFee = calculateExtraFee(latestWeatherObservation, vehicleType);
        return baseFee + extraFee;
    }
    private double calculateBaseFee(String city, String vehicleType) {
        System.out.println(city + " " + vehicleType);
        BaseFeeRule rule = baseFeeRuleRepository.findByCityAndVehicleType(city, vehicleType);
        if (rule == null) throw new IllegalArgumentException("No base fee found");
        return rule.getFee();
    }
    private double calculateExtraFee(WeatherObservation weatherObservation, String vehicleType) {
        double totalExtraFee = 0.0;
        List<ExtraFeeRule> extraFeeRules = extraFeeRuleRepository.findByVehicleType(vehicleType);

        for (ExtraFeeRule rule : extraFeeRules) {
            totalExtraFee += applyRule(rule, weatherObservation);
        }
        return totalExtraFee;
    }

    private double applyRule(ExtraFeeRule rule, WeatherObservation weatherObservation) {
        return switch (rule.getCondition()) {
            case "air temperature" ->
                    isValueInRange(weatherObservation.getAirTemperature(), rule) ? rule.getFee() : 0.0;
            case "wind speed" ->
                    isValueInRange(weatherObservation.getWindSpeed(), rule) ? rule.getFee() : 0.0;
            case "weather phenomenon" ->
                    isWeatherPhenomenonMatch(weatherObservation.getWeatherPhenomenon(), rule.getWeatherPhenomenonType()) ? rule.getFee() : 0.0;
            default -> throw new IllegalArgumentException("Invalid condition: " + rule.getCondition());
        };
    }

    private boolean isValueInRange(Double value, ExtraFeeRule rule) {
        Double min = rule.getMinConditionValue();
        Double max = rule.getMaxConditionValue();
        boolean minInRange = rule.isMinIncludedInRange();
        boolean maxInRange = rule.isMaxIncludedInRange();

        if (min == null && max == null) return true; // ~ min and max are null, so any value is considered within range
        if (min == null) return maxInRange ? value <= max : value < max; // Only max is provided, check if value is less than or equal to max
        if (max == null) return minInRange ? value >= min : value > min; // Only min is provided, check if value is greater than or equal to min
        if(minInRange && maxInRange) return value >= min && value <= max; // Check if value is between min and max
        if(minInRange) return value >= min && value < max; // Check if value is between min and max
        if(maxInRange) return value > min && value <= max; // Check if value is between min and max
        return  value > min && value < max;
    }

    private boolean isWeatherPhenomenonMatch(String weatherPhenomenon, Set<String> conditionValues) {
        return conditionValues.contains(weatherPhenomenon.toLowerCase());
    }
}

