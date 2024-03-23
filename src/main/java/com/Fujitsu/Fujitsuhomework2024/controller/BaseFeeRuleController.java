package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base-fees")
@RequiredArgsConstructor
public class BaseFeeRuleController {
    private RuleService ruleService;

    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = ruleService.getAllBaseFeeRules();
        return ResponseEntity.ok(baseFeeRules);
    }

    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule createdBaseFeeRule = ruleService.createBaseFeeRule(baseFeeRule);
        return new ResponseEntity<>(createdBaseFeeRule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        BaseFeeRule baseFeeRule = ruleService.getBaseFeeRuleById(id);
        return ResponseEntity.ok(baseFeeRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule updatedBaseFeeRule = ruleService.updateBaseFeeRule(id, baseFeeRule);
        return ResponseEntity.ok(updatedBaseFeeRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        ruleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
