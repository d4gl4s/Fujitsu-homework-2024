package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.service.RuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/extra-fees")
@RequiredArgsConstructor
public class ExtraFeeRuleController {
    private final RuleService ruleService;

    /**
     * Retrieves all extra fee rules.
     *
     * @return A {@link ResponseEntity} containing a list of {@link ExtraFeeRule} objects.
     */
    @GetMapping
    public ResponseEntity<List<ExtraFeeRule>> getAllExtraFeeRules() {
        List<ExtraFeeRule> extraFeeRules = ruleService.getAllExtraFeeRules();
        return ResponseEntity.ok(extraFeeRules);
    }

    /**
     * Creates a new extra fee rule.
     *
     * @param extraFeeRule The extra fee rule to be created, provided in the request body.
     * @return A {@link ResponseEntity} containing the created {@link ExtraFeeRule} object.
     */
    @PostMapping
    public ResponseEntity<ExtraFeeRule> createExtraFeeRule(@RequestBody ExtraFeeRule extraFeeRule) {
        ExtraFeeRule createdExtraFeeRule = ruleService.createExtraFeeRule(extraFeeRule);
        return new ResponseEntity<>(createdExtraFeeRule, HttpStatus.CREATED);
    }

    /**
     * Retrieves an extra fee rule by its ID.
     *
     * @param id The ID of the extra fee rule to retrieve.
     * @return A {@link ResponseEntity} containing the retrieved {@link ExtraFeeRule} object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> getExtraFeeRuleById(@PathVariable Long id) {
        ExtraFeeRule extraFeeRule = ruleService.getExtraFeeRuleById(id);
        return ResponseEntity.ok(extraFeeRule);
    }

    /**
     * Updates an existing extra fee rule. Keeps original for requests for extra fee rules at previous dateTimes.
     *
     * @param id          The ID of the extra fee rule to update.
     * @param extraFeeRule The updated extra fee rule, provided in the request body.
     * @return A {@link ResponseEntity} containing the updated {@link ExtraFeeRule} object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExtraFeeRule> updateExtraFeeRule(@PathVariable Long id, @RequestBody ExtraFeeRule extraFeeRule) {
        ExtraFeeRule updatedExtraFeeRule = ruleService.updateExtraFeeRule(id, extraFeeRule);
        return ResponseEntity.ok(updatedExtraFeeRule);
    }

    /**
     * Deletes an extra fee rule by its ID by specifying an end date for rule. Keeps original for requests for extra fee rules at previous dateTimes.
     *
     * @param id The ID of the extra fee rule to delete.
     * @return A {@link ResponseEntity} indicating success or failure of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtraFeeRule(@PathVariable Long id) {
        ruleService.deleteExtraFeeRule(id);
        return ResponseEntity.noContent().build();
    }
}
