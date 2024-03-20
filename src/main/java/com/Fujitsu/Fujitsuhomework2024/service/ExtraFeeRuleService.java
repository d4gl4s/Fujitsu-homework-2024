package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtraFeeRuleService {

    private final ExtraFeeRuleRepository extraFeeRuleRepository;

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

