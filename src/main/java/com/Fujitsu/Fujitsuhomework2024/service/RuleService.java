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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final BaseFeeRuleRepository baseFeeRuleRepository;
    private final ExtraFeeRuleRepository extraFeeRuleRepository;

    // Base Fee Rule CRUD operations

    /**
     * Retrieves all base fee rules.
     *
     * @return A list of all base fee rules.
     */
    public List<BaseFeeRule> getAllBaseFeeRules() {
        return baseFeeRuleRepository.findAll();
    }

    /**
     * Creates a new base fee rule.
     *
     * @param baseFeeRule The base fee rule to be created.
     * @return The created base fee rule.
     */
    public BaseFeeRule createBaseFeeRule(BaseFeeRule baseFeeRule) {
        validateBaseFeeRule(baseFeeRule);
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    /**
     * Retrieves a base fee rule by its ID.
     *
     * @param id The ID of the base fee rule to retrieve.
     * @return The base fee rule with the specified ID.
     * @throws ResourceNotFoundException if no base fee rule is found with the given ID.
     */
    public BaseFeeRule getBaseFeeRuleById(Long id) {
        return baseFeeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Base fee rule not found with id: " + id));
    }

    /**
     * Updates an existing base fee rule.
     *
     * @param id          The ID of the base fee rule to update.
     * @param baseFeeRule The updated base fee rule.
     * @return The updated base fee rule.
     * @throws ResourceNotFoundException if no base fee rule is found with the given ID.
     */
    public BaseFeeRule updateBaseFeeRule(Long id, BaseFeeRule baseFeeRule) {
        validateBaseFeeRule(baseFeeRule);
        BaseFeeRule existingRule = getBaseFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);
        baseFeeRule.setId(null); // Clear ID to create a new entry
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    /**
     * Deletes a base fee rule by its ID.
     *
     * @param id The ID of the base fee rule to delete.
     * @throws ResourceNotFoundException if no base fee rule is found with the given ID.
     */
    public void deleteBaseFeeRule(Long id) {
        BaseFeeRule existingRule = getBaseFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        baseFeeRuleRepository.save(existingRule);
    }

    // Extra Fee Rule CRUD operations

    /**
     * Retrieves all extra fee rules.
     *
     * @return List of all extra fee rules.
     */
    public List<ExtraFeeRule> getAllExtraFeeRules() {
        return extraFeeRuleRepository.findAll();
    }

    /**
     * Creates a new extra fee rule.
     *
     * @param extraFeeRule The extra fee rule to create.
     * @return The created extra fee rule.
     */
    public ExtraFeeRule createExtraFeeRule(ExtraFeeRule extraFeeRule) {
        validateExtraFeeRule(extraFeeRule);
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    /**
     * Retrieves an extra fee rule by its ID.
     *
     * @param id The ID of the extra fee rule to retrieve.
     * @return The extra fee rule with the specified ID.
     * @throws ResourceNotFoundException if no extra fee rule is found with the given ID.
     */
    public ExtraFeeRule getExtraFeeRuleById(Long id) {
        return extraFeeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Extra fee rule not found with id: " + id));
    }

    /**
     * Updates an existing extra fee rule.
     *
     * @param id           The ID of the extra fee rule to update.
     * @param extraFeeRule The updated extra fee rule.
     * @return The updated extra fee rule.
     * @throws ResourceNotFoundException if no extra fee rule is found with the given ID.
     */
    public ExtraFeeRule updateExtraFeeRule(Long id, ExtraFeeRule extraFeeRule) {
        validateExtraFeeRule(extraFeeRule);
        ExtraFeeRule existingRule = getExtraFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    /**
     * Deletes an extra fee rule by its ID.
     *
     * @param id The ID of the extra fee rule to delete.
     * @throws ResourceNotFoundException if no extra fee rule is found with the given ID.
     */
    public void deleteExtraFeeRule(Long id) {
        ExtraFeeRule existingRule = getExtraFeeRuleById(id);
        existingRule.setEndDate(LocalDateTime.now());
        extraFeeRuleRepository.save(existingRule);
    }


    // Fee Rule Validation

    // Validates base fee rule data
    private void validateBaseFeeRule(BaseFeeRule baseFeeRule) {
        if(baseFeeRule.getCity() == null)
            throw new IllegalArgumentException("Base Fee City value can not be null");

        if(baseFeeRule.getVehicleType() == null)
            throw new IllegalArgumentException("Base Fee Vehicle Type value can not be null");

        if(baseFeeRule.getFee() <= 0)
            throw new IllegalArgumentException("Base Fee value must be greater than 0");
    }

    // Validates extra fee rule data
    private void validateExtraFeeRule(ExtraFeeRule extraFeeRule) {
        Set<VehicleType> vehicleTypes = extraFeeRule.getVehicleType();
        String condition = extraFeeRule.getCondition();
        Double minValue = extraFeeRule.getMinConditionValue();
        Double maxValue = extraFeeRule.getMaxConditionValue();
        Set<String> weatherPhenomenons = extraFeeRule.getWeatherPhenomenonType();

        if(vehicleTypes == null || vehicleTypes.isEmpty())
            throw new IllegalArgumentException("Vehicle types must be specified for extra fee rules");

        if(condition == null || condition.isEmpty())
            throw new IllegalArgumentException("Condition can not be empty for extra fee rules");

        if(!minValue.isNaN() && !maxValue.isNaN() && minValue >= maxValue)
            throw new IllegalArgumentException("Min value must be smaller than max value");

        if(weatherPhenomenons != null && weatherPhenomenons.isEmpty())
            throw new IllegalArgumentException("Weather phenomenons list can not be empty for extra fee rules");
    }

    // Other utility methods

    /**
     * Retrieves the base fee rule for a given city, vehicle type, and date/time.
     *
     * @param city        The city for which to retrieve the base fee rule.
     * @param vehicleType The type of vehicle for which to retrieve the base fee rule.
     * @param dateTime    The date/time for which to retrieve the base fee rule.
     * @return The base fee rule for the specified city, vehicle type, and date/time.
     * @throws ResourceNotFoundException if no base fee rule is found for the given city and vehicle type.
     */
    public BaseFeeRule getBaseFeeRuleByCityAndVehicleTypeAndDateTime(City city, VehicleType vehicleType, LocalDateTime dateTime) {
        return baseFeeRuleRepository.findByCityAndVehicleTypeAndDateTime(city, vehicleType, dateTime)
                .orElseThrow(() -> new ResourceNotFoundException("Base fee not found for given city and vehicle type"));
    }

    /**
     * Retrieves a list of extra fee rules for a given vehicle type and date/time.
     *
     * @param vehicleType The type of vehicle for which to retrieve extra fee rules.
     * @param dateTime    The date/time for which to retrieve extra fee rules.
     * @return A list of extra fee rules for the specified vehicle type and date/time.
     */
    public List<ExtraFeeRule> findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType vehicleType, LocalDateTime dateTime) {
        return extraFeeRuleRepository.findByDateTimeAndVehicleType(dateTime, vehicleType);
    }


}
