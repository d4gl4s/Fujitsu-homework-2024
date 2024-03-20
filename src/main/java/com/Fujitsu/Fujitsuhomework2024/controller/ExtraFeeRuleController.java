package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extra-fees")
@RequiredArgsConstructor
public class ExtraFeeRuleController {

    private final ExtraFeeRuleService extraFeeRuleService;

    @GetMapping
    public ResponseEntity<List<ExtraFeeRule>> getAllExtraFeeRules() {
        List<ExtraFeeRule> extraFeeRules = extraFeeRuleService.getAllExtraFeeRules();
        return ResponseEntity.ok(extraFeeRules);
    }

    @PostMapping
    public ResponseEntity<ExtraFeeRule> createExtraFeeRule(@RequestBody ExtraFeeRule extraFeeRule) {
        ExtraFeeRule createdExtraFeeRule = extraFeeRuleService.createExtraFeeRule(extraFeeRule);
        return new ResponseEntity<>(createdExtraFeeRule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> getExtraFeeRuleById(@PathVariable Long id) {
        ExtraFeeRule extraFeeRule = extraFeeRuleService.getExtraFeeRuleById(id);
        return ResponseEntity.ok(extraFeeRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> updateExtraFeeRule(@PathVariable Long id, @RequestBody ExtraFeeRule extraFeeRule) {
        ExtraFeeRule updatedExtraFeeRule = extraFeeRuleService.updateExtraFeeRule(id, extraFeeRule);
        return ResponseEntity.ok(updatedExtraFeeRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtraFeeRule(@PathVariable Long id) {
        extraFeeRuleService.deleteExtraFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
