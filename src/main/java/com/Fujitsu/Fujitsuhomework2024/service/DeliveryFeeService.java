package com.Fujitsu.Fujitsuhomework2024.service;



import com.Fujitsu.Fujitsuhomework2024.enums.*;
import com.Fujitsu.Fujitsuhomework2024.exception.ForbiddenVehicleTypeException;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryFeeService {
    private final WeatherService weatherService;
    private final RuleService ruleService;

    public double calculateDeliveryFee(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        if (city == null || vehicleType == null)
            throw new IllegalArgumentException("City and vehicle type must not be null");

        if (dateTime != null && dateTime.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Date cannot be in the future");

        double baseFee = calculateBaseFee(city, vehicleType, dateTime);
        WeatherObservation weatherObservation = weatherService.getWeatherAtDateTimeAtCity(dateTime, city);
        double extraFee = calculateExtraFee(weatherObservation, vehicleType, dateTime);
        return baseFee + extraFee;
    }
    private double calculateBaseFee(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        BaseFeeRule rule = ruleService.getBaseFeeRuleByCityAndVehicleTypeAndDateTime(city, vehicleType, dateTime);
        if (rule == null) throw new IllegalArgumentException("No base fee found");
        return rule.getFee();
    }
    private double calculateExtraFee(WeatherObservation weatherObservation, VehicleType vehicleType, LocalDateTime dateTime) {
        return ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(vehicleType, dateTime)
                .stream()
                .mapToDouble(rule -> applyRule(rule, weatherObservation))
                .sum();
    }

    private double applyRule(ExtraFeeRule rule, WeatherObservation weatherObservation) {
        double fee = rule.getFee();
        switch (rule.getCondition()) {
            case "air temperature" -> {
                if (isValueInRange(weatherObservation.getAirTemperature(), rule))
                    return handleFee(fee);
            }
            case "wind speed" -> {
                if (isValueInRange(weatherObservation.getWindSpeed(), rule))
                    return handleFee(fee);
            }
            case "weather phenomenon" -> {
                if (isWeatherPhenomenonMatch(weatherObservation.getWeatherPhenomenon(), rule.getWeatherPhenomenonType()))
                    return handleFee(fee);
            }
            default -> throw new IllegalArgumentException("Invalid condition: " + rule.getCondition());
        }
        return 0;
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

    private double handleFee(double fee){
        if(fee == -1) throw new ForbiddenVehicleTypeException();
        return fee;
    }
}

