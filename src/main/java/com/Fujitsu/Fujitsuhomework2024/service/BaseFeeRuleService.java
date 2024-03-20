package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseFeeRuleService {

    private final BaseFeeRuleRepository baseFeeRuleRepository;

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
}
