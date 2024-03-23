package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.exception.ResourceNotFoundException;
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

    // Base Fee Rule CRUD operations

    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }

    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public BaseFeeRule getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Base fee rule not found with id: " + id));
    }

    public BaseFeeRule updateBaseFeeRule(Long id, BaseFeeRule baseFeeRule) {
        BaseFeeRule existingRule = getBaseFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);
        baseFeeRule.setId(null); // Clear ID to create a new entry
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public void deleteBaseFeeRule(Long id) {
        BaseFeeRule existingRule = getBaseFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);
    }

    // Extra Fee Rule CRUD operations

    public List<ExtraFeeRule> getAllExtraFeeRules() {
        return extraFeeRuleRepository.findAll();
    }

    public ExtraFeeRule createExtraFeeRule(ExtraFeeRule extraFeeRule) {
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public ExtraFeeRule getExtraFeeRuleById(Long id) {
        return extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Extra fee rule not found with id: " + id));
    }

    public ExtraFeeRule updateExtraFeeRule(Long id, ExtraFeeRule extraFeeRule) {
        ExtraFeeRule existingRule = getExtraFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public void deleteExtraFeeRule(Long id) {
        ExtraFeeRule existingRule = getExtraFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);
    }

    // Other utility methods

    public BaseFeeRule getBaseFeeRuleByCityAndVehicleTypeAndDateTime(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        return baseFeeRuleRepository.findByCityAndVehicleTypeAndDateTime(city, vehicleType, dateTime)
                .orElseThrow(() -> new ResourceNotFoundException("No base fee found"));
    }

    public List<ExtraFeeRule> findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType vehicleType, LocalDateTime dateTime) {
        return extraFeeRuleRepository.findByDateTimeAndVehicleType(dateTime, vehicleType);
    }
}
