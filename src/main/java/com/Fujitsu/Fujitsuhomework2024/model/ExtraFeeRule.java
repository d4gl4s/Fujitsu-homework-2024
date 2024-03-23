package com.Fujitsu.Fujitsuhomework2024.model;

import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "extra_fee_rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ElementCollection
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
    @ElementCollection
    private Set<String> weatherPhenomenonType; // for weather phenomenon (snow, sleet, rain)

    @Column(nullable = false)
    private double fee;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

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

    public ExtraFeeRule(Set<VehicleType> vehicleType, String condition, Set<String> weatherPhenomenonType, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.weatherPhenomenonType = weatherPhenomenonType;
        this.fee = fee;
        this.startDate = LocalDateTime.now();
    }
}
