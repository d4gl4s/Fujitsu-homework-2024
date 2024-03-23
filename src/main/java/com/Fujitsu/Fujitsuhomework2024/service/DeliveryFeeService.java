package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.exception.ForbiddenVehicleTypeException;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryFeeService {
    private final WeatherRepository weatherRepository;
    private final BaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeRuleRepository extraFeeRuleRepository;
    private final Map<City, String> cityStationMap = new HashMap<>();
    {
        cityStationMap.put(City.TALLINN, "Tallinn-Harku");
        cityStationMap.put(City.TARTU, "Tartu-Tõravere");
        cityStationMap.put(City.PÄRNU, "Pärnu");
    }

    public double calculateDeliveryFee(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        if (city == null || vehicleType == null)
            throw new IllegalArgumentException("City and vehicle type must not be null");
        double baseFee = calculateBaseFee(city, vehicleType);
        WeatherObservation latestWeatherObservation = weatherRepository.findFirstByStationNameOrderByTimestampDesc(cityStationMap.get(city));
        double extraFee = calculateExtraFee(latestWeatherObservation, vehicleType);
        return baseFee + extraFee;
    }
    private double calculateBaseFee(City city, VehicleType vehicleType) {
        BaseFeeRule rule = baseFeeRuleRepository.findByCityAndVehicleType(city, vehicleType);
        if (rule == null) throw new IllegalArgumentException("No base fee found");
        return rule.getFee();
    }
    private double calculateExtraFee(WeatherObservation weatherObservation, VehicleType vehicleType) {
        double totalExtraFee = 0.0;
        List<ExtraFeeRule> extraFeeRules = extraFeeRuleRepository.findByVehicleType(vehicleType);
        System.out.println(weatherObservation);
        for (ExtraFeeRule rule : extraFeeRules) {
            System.out.println(rule);
            totalExtraFee += applyRule(rule, weatherObservation);
        }
        System.out.println("Extra fee = " + totalExtraFee);
        return totalExtraFee;
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

