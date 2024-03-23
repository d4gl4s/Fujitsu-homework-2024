package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final BaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }

    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public BaseFeeRule getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Base fee rule not found with id: " + id));
    }

    public BaseFeeRule updateBaseFeeRule(Long id, BaseFeeRule baseFeeRule) {
        BaseFeeRule existingRule = baseFeeRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Base fee rule not found with id: " + id));

        // Update the end date of the existing rule to current date
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);

        // Create a new rule with modifications and save it
        BaseFeeRule newRuleCopy = new BaseFeeRule(
                baseFeeRule.getCity(),
                baseFeeRule.getVehicleType(),
                baseFeeRule.getFee());
        return baseFeeRuleRepository.save(newRuleCopy);
    }

    public void deleteBaseFeeRule(Long id) {
        BaseFeeRule existingRule = baseFeeRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Base fee rule not found with id: " + id));

        // Update the end date of the rule to current date, but keep it in database
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);
    }
    public List<ExtraFeeRule> getAllExtraFeeRules() {
        return extraFeeRuleRepository.findAll();
    }

    public ExtraFeeRule createExtraFeeRule(ExtraFeeRule extraFeeRule) {
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public ExtraFeeRule getExtraFeeRuleById(Long id) {
        return extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Extra fee rule not found with id: " + id));
    }

    public ExtraFeeRule updateExtraFeeRule(Long id, ExtraFeeRule extraFeeRule) {
        ExtraFeeRule existingRule = extraFeeRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Extra fee rule not found with id: " + id));

        // Update the end date of the existing rule to current date
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);

        // Create a new rule with modifications and save it
        ExtraFeeRule newRuleCopy = new ExtraFeeRule();
        newRuleCopy.setCondition(extraFeeRule.getCondition());
        newRuleCopy.setVehicleType(extraFeeRule.getVehicleType());
        newRuleCopy.setFee(extraFeeRule.getFee());
        if(existingRule.getCondition().equals("weather phenomenon")){
            newRuleCopy.setWeatherPhenomenonType(extraFeeRule.getWeatherPhenomenonType());
            return newRuleCopy;
        }
        newRuleCopy.setMaxConditionValue(extraFeeRule.getMaxConditionValue());
        newRuleCopy.setMinConditionValue(extraFeeRule.getMinConditionValue());
        newRuleCopy.setMaxIncludedInRange(extraFeeRule.isMaxIncludedInRange());
        newRuleCopy.setMinIncludedInRange(extraFeeRule.isMinIncludedInRange());
        return extraFeeRuleRepository.save(newRuleCopy);
    }

    public void deleteExtraFeeRule(Long id) {
        ExtraFeeRule existingRule = extraFeeRuleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Extra fee rule not found with id: " + id));

        // Update the end date of the existing rule to current date
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);
    }

    public BaseFeeRule getBaseFeeRuleByCityAndVehicleTypeAndDateTime(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        BaseFeeRule baseFeeRule = baseFeeRuleRepository.findByCityAndVehicleTypeAndDateTime(city, vehicleType, dateTime);
        if (baseFeeRule == null) throw new IllegalArgumentException("No base fee found");
        return baseFeeRule;
    }

    public List<ExtraFeeRule> findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType vehicleType, LocalDateTime dateTime) {
        return extraFeeRuleRepository.findByDateTimeAndVehicleType(dateTime, vehicleType);
    }
}
