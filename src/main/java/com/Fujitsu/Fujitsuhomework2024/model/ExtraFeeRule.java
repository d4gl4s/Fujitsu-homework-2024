package com.Fujitsu.Fujitsuhomework2024.model;

import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "extra_fee_rules")
@Data
@RequiredArgsConstructor
public class ExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<VehicleType> vehicleType;

    @Column(nullable = false)
    private String condition; // e.g., temperature, wind speed, weather phenomenon

    @Column
    private Double minConditionValue; // for temperature & wind speed ranges

    @Column
    private Double maxConditionValue; // for temperature & wind speed ranges
    @Column
    private boolean minIncludedInRange;

    @Column
    private boolean maxIncludedInRange;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> weatherPhenomenonType; // for weather phenomenon (snow, sleet, rain)

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;  // End date is specified if it does not apply currently. If rule is in use at the present moment, endDate will be null

    /**
    * Constructs an {@code ExtraFeeRule} object with the specified parameters.
    *
    * @param vehicleType           The set of vehicle types to which this rule applies.
    * @param condition             The condition under which this rule applies.
    * @param minConditionValue     The minimum value of the condition.
    * @param maxConditionValue     The maximum value of the condition.
    * @param minIncludedInRange    {@code true} if the minimum value is included in the range, {@code false} otherwise.
    * @param maxIncludedInRange    {@code true} if the maximum value is included in the range, {@code false} otherwise.
    * @param fee                   The fee associated with this rule.
    */
    public ExtraFeeRule(Set<VehicleType> vehicleType, String condition, Double minConditionValue, Double maxConditionValue, boolean minIncludedInRange, boolean maxIncludedInRange, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.minConditionValue = minConditionValue;
        this.maxConditionValue = maxConditionValue;
        this.minIncludedInRange = minIncludedInRange;
        this.maxIncludedInRange = maxIncludedInRange;
        this.fee = fee;
        this.startDate = LocalDateTime.now();
    }

    /**
    * Constructs an {@code ExtraFeeRule} object with the specified parameters.
    *
    * @param vehicleType           The set of vehicle types to which this rule applies.
    * @param condition             The condition under which this rule applies.
    * @param weatherPhenomenonType The set of weather phenomenon types to which this rule applies.
    * @param fee                   The fee associated with this rule.
    */
    public ExtraFeeRule(Set<VehicleType> vehicleType, String condition, Set<String> weatherPhenomenonType, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.weatherPhenomenonType = weatherPhenomenonType;
        this.fee = fee;
        this.startDate = LocalDateTime.now();
    }
}
