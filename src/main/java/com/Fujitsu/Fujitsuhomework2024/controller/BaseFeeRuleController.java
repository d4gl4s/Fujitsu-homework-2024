package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.service.BaseFeeRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/base-fees")
@RequiredArgsConstructor
public class BaseFeeRuleController {

    private BaseFeeRuleService baseFeeRuleService;

    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = baseFeeRuleService.getAllBaseFeeRules();
        return ResponseEntity.ok(baseFeeRules);
    }

    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule createdBaseFeeRule = baseFeeRuleService.createBaseFeeRule(baseFeeRule);
        return new ResponseEntity<>(createdBaseFeeRule, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        BaseFeeRule baseFeeRule = baseFeeRuleService.getBaseFeeRuleById(id);
        return ResponseEntity.ok(baseFeeRule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule updatedBaseFeeRule = baseFeeRuleService.updateBaseFeeRule(id, baseFeeRule);
        return ResponseEntity.ok(updatedBaseFeeRule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        baseFeeRuleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
