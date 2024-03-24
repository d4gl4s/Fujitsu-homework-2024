package com.Fujitsu.Fujitsuhomework2024.service;



import com.Fujitsu.Fujitsuhomework2024.enums.*;
import com.Fujitsu.Fujitsuhomework2024.exception.ForbiddenVehicleTypeException;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryFeeService {
    private final WeatherService weatherService;
    private final RuleService ruleService;

    /**
     * Calculates the delivery fee based on the specified city and vehicle type.
     * If dateTime is provided returns fee that would have been calculated with rules that were present during that time period
     *
     * @param city       The city where the delivery is to be made.
     * @param vehicleType The type of vehicle to be used for the delivery.
     * @param dateTime   The date and time of the delivery.
     * @return The calculated delivery fee.
     * @throws IllegalArgumentException if the city or vehicle type is null, or if the date/time is in the future.
     */
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


    // Calculates the base fee for a given city, vehicle type, and date/time.
    private double calculateBaseFee(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        BaseFeeRule rule = ruleService.getBaseFeeRuleByCityAndVehicleTypeAndDateTime(city, vehicleType, dateTime);
        return rule.getFee();
    }

    // Calculates the extra fee based on weather observations, vehicle type, and date/time.
    private double calculateExtraFee(WeatherObservation weatherObservation, VehicleType vehicleType, LocalDateTime dateTime) {
        return ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(vehicleType, dateTime)
                .stream()
                .mapToDouble(rule -> applyRule(rule, weatherObservation))
                .sum();
    }

    // Applies a specific rule to determine if an extra fee should be added based on weather conditions.
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

    // Checks if a given value falls within the range specified by an extra fee rule.
    private boolean isValueInRange(Double value, ExtraFeeRule rule) {
        Double min = rule.getMinConditionValue();
        Double max = rule.getMaxConditionValue();
        boolean minInRange = rule.isMinIncludedInRange();
        boolean maxInRange = rule.isMaxIncludedInRange();

        if (min == null && max == null) return true;
        if (min == null) return maxInRange ? value <= max : value < max;
        if (max == null) return minInRange ? value >= min : value > min;

        if(minInRange && maxInRange) return value >= min && value <= max;
        if(minInRange) return value >= min && value < max;
        if(maxInRange) return value > min && value <= max;
        return  value > min && value < max;
    }

    // Checks if a weather phenomenon matches the condition specified in an extra fee rule.
    private boolean isWeatherPhenomenonMatch(String weatherPhenomenon, Set<String> conditionValues) {
        if(weatherPhenomenon == null) return false;
        String lowerCase = weatherPhenomenon.toLowerCase();
        String capitalized = lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
        return conditionValues.contains(capitalized);
    }

    // Handles special cases where a fee of -1 indicates that a specific vehicle type is forbidden.
    private double handleFee(double fee){
        if(fee == -1) throw new ForbiddenVehicleTypeException("Usage of selected vehicle type is forbidden");
        return fee;
    }

}

