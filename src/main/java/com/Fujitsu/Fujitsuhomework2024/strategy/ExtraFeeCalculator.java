package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.Weather;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExtraFeeCalculator {
    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    public double calculateExtraFee(Weather weather, String vehicleType) {
        if (weather == null || vehicleType == null)
            throw new IllegalArgumentException("Weather and vehicle type must not be null");

        double totalExtraFee = 0.0;
        List<ExtraFeeRule> extraFeeRules = extraFeeRuleRepository.findByVehicleType(vehicleType);

        for (ExtraFeeRule rule : extraFeeRules) {
            totalExtraFee += applyRule(rule, weather);
        }
        return totalExtraFee;
    }

    private double applyRule(ExtraFeeRule rule, Weather weather) {
        return switch (rule.getCondition()) {
            case "air temperature" ->
                    isValueInRange(weather.getAirTemperature(), rule.getMinConditionValue(), rule.getMaxConditionValue()) ? rule.getFee() : 0.0;
            case "wind speed" ->
                    isValueInRange(weather.getWindSpeed(), rule.getMinConditionValue(), rule.getMaxConditionValue()) ? rule.getFee() : 0.0;
            case "weather phenomenon" ->
                    isWeatherPhenomenonMatch(weather.getWeatherPhenomenon(), rule.getWeatherPhenomenonType()) ? rule.getFee() : 0.0;
            default -> throw new IllegalArgumentException("Invalid condition: " + rule.getCondition());
        };
    }

    private boolean isValueInRange(Double value, Double min, Double max) {
        if (min == null && max == null) return true; // ~ min and max are null, so any value is considered within range
        if (min == null) return value <= max; // Only max is provided, check if value is less than or equal to max
        if (max == null) return value >= min; // Only min is provided, check if value is greater than or equal to min
        return value >= min && value <= max; // Check if value is between min and max
    }

    private boolean isWeatherPhenomenonMatch(String weatherPhenomenon, Set<String> conditionValues) {
        return conditionValues.contains(weatherPhenomenon.toLowerCase());
    }
}


