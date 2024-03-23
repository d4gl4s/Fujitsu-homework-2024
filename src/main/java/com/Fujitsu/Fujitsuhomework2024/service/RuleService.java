package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (!baseFeeRuleRepository.existsById(id)) {
            throw new RuntimeException("Base fee rule not found with id: " + id);
        }
        baseFeeRule.setId(id); // Ensure the ID is set for update operation
        return baseFeeRuleRepository.save(baseFeeRule);
    }

    public void deleteBaseFeeRule(Long id) {
        if (!baseFeeRuleRepository.existsById(id)) {
            throw new RuntimeException("Base fee rule not found with id: " + id);
        }
        baseFeeRuleRepository.deleteById(id);
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
        if (!extraFeeRuleRepository.existsById(id)) {
            throw new RuntimeException("Extra fee rule not found with id: " + id);
        }
        extraFeeRule.setId(id); // Ensure the ID is set for update operation
        return extraFeeRuleRepository.save(extraFeeRule);
    }

    public void deleteExtraFeeRule(Long id) {
        if (!extraFeeRuleRepository.existsById(id)) {
            throw new RuntimeException("Extra fee rule not found with id: " + id);
        }
        extraFeeRuleRepository.deleteById(id);
    }
}
