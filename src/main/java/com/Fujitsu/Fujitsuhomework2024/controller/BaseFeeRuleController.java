package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/base-fees")
@RequiredArgsConstructor
public class BaseFeeRuleController {
    private final RuleService ruleService;

    /**
     * Retrieves all base fee rules.
     *
     * @return A {@link ResponseEntity} containing a list of {@link BaseFeeRule} objects.
     */
    @GetMapping
    public ResponseEntity<List<BaseFeeRule>> getAllBaseFeeRules() {
        List<BaseFeeRule> baseFeeRules = ruleService.getAllBaseFeeRules();
        return ResponseEntity.ok(baseFeeRules);
    }

    /**
     * Creates a new base fee rule.
     *
     * @param baseFeeRule The base fee rule to be created, provided in the request body.
     * @return A {@link ResponseEntity} containing the created {@link BaseFeeRule} object.
     */
    @PostMapping
    public ResponseEntity<BaseFeeRule> createBaseFeeRule(@RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule createdBaseFeeRule = ruleService.createBaseFeeRule(baseFeeRule);
        return new ResponseEntity<>(createdBaseFeeRule, HttpStatus.CREATED);
    }

    /**
     * Retrieves a base fee rule by its ID.
     *
     * @param id The ID of the base fee rule to retrieve.
     * @return A {@link ResponseEntity} containing the retrieved {@link BaseFeeRule} object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseFeeRule> getBaseFeeRuleById(@PathVariable Long id) {
        BaseFeeRule baseFeeRule = ruleService.getBaseFeeRuleById(id);
        return ResponseEntity.ok(baseFeeRule);
    }

    /**
     * Updates an existing base fee rule. Keeps original for requests for rules at previous dateTimes
     *
     * @param id          The ID of the base fee rule to update.
     * @param baseFeeRule The updated base fee rule, provided in the request body.
     * @return A {@link ResponseEntity} containing the updated {@link BaseFeeRule} object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseFeeRule> updateBaseFeeRule(@PathVariable Long id, @RequestBody BaseFeeRule baseFeeRule) {
        BaseFeeRule updatedBaseFeeRule = ruleService.updateBaseFeeRule(id, baseFeeRule);
        return ResponseEntity.ok(updatedBaseFeeRule);
    }

    /**
     * Deletes a base fee rule by its ID. Keeps rule in history for requests for rules at previous dateTimes
     *
     * @param id The ID of the base fee rule to delete.
     * @return A {@link ResponseEntity} indicating success or failure of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseFeeRule(@PathVariable Long id) {
        ruleService.deleteBaseFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
