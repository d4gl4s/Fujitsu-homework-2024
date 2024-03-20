package com.Fujitsu.Fujitsuhomework2024.strategy;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseFeeCalculator  {
    private final BaseFeeRuleRepository baseFeeRuleRepository;
    public double calculateBaseFee(String city, String vehicleType) {
        BaseFeeRule rule = baseFeeRuleRepository.findByCityAndVehicleType(city, vehicleType);
        if (rule == null){
            throw new IllegalArgumentException("Invalid city or vehicle type");
        }
        return rule.getFee();
    }
}

