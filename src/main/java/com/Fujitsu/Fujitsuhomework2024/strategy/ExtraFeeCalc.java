package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.Weather;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExtraFeeCalc {
    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    public double calculateExtraFee(Weather weather, String vehicleType) {
        // Initialize total extra fee
        double totalExtraFee = 0.0;

        // Fetch rules from repository based on vehicle type (ignoring city)
        List<ExtraFeeRule> rules = extraFeeRuleRepository.findByVehicleType(vehicleType);

        // Apply rules to calculate extra fee
        for (ExtraFeeRule rule : rules) {
            switch (rule.getCondition()) {
                case "temperature" -> {
                    if (isTemperatureInRange(weather.getAirTemperature(), rule.getConditionValue())) {
                        totalExtraFee += rule.getFee();
                    }
                }
                case "wind speed" -> {
                    if (isWindSpeedInRange(weather.getWindSpeed(), rule.getConditionValue())) {
                        totalExtraFee += rule.getFee();
                    }
                }
                case "weather phenomenon" -> {
                    if (isWeatherPhenomenonMatch(weather.getWeatherPhenomenon(), rule.getConditionValue())) {
                        totalExtraFee += rule.getFee();
                    }
                }
                default -> throw new IllegalArgumentException("Invalid condition: " + rule.getCondition());
            }
        }

        return totalExtraFee;
    }

    private boolean isTemperatureInRange(double temperature, String conditionValue) {
        String[] range = conditionValue.split("-");
        double minTemperature = Double.parseDouble(range[0]);
        double maxTemperature = Double.parseDouble(range[1]);
        return temperature >= minTemperature && temperature < maxTemperature;
    }

    private boolean isWindSpeedInRange(double windSpeed, String conditionValue) {
        String[] range = conditionValue.split("-");
        double minWindSpeed = Double.parseDouble(range[0]);
        double maxWindSpeed = Double.parseDouble(range[1]);
        return windSpeed >= minWindSpeed && windSpeed < maxWindSpeed;
    }

    private boolean isWeatherPhenomenonMatch(String weatherPhenomenon, String conditionValue) {
        return conditionValue.equalsIgnoreCase(weatherPhenomenon);
    }
}

