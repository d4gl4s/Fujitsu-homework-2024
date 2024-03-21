package com.Fujitsu.Fujitsuhomework2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column
    @ElementCollection
    private Set<String> vehicleType;

    @Column(nullable = false)
    private String condition; // e.g., temperature, wind speed, weather phenomenon

    @Column
    private Double minConditionValue; // for temperature & wind speed ranges

    @Column
    private Double maxConditionValue; // for temperature & wind speed ranges

    @Column
    @ElementCollection
    private Set<String> weatherPhenomenonType; // for weather phenomenon (snow, sleet, rain)

    @Column(nullable = false)
    private double fee;

    @Column
    private String errorMessage;

    public ExtraFeeRule(Set<String> vehicleType, String condition, Set<String> weatherPhenomenonType, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.weatherPhenomenonType = weatherPhenomenonType;
        this.fee = fee;
    }

    public ExtraFeeRule(Set<String> vehicleType, String condition, Double minConditionValue, Double maxConditionValue, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.minConditionValue = minConditionValue;
        this.maxConditionValue = maxConditionValue;
        this.fee = fee;
    }

    public ExtraFeeRule(Set<String> vehicleType, String condition, Double minConditionValue, Double maxConditionValue, String errorMessage) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.minConditionValue = minConditionValue;
        this.maxConditionValue = maxConditionValue;
        this.errorMessage = errorMessage;
    }

    public ExtraFeeRule(Set<String> vehicleType, String condition, Set<String> weatherPhenomenonType, String errorMessage) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.weatherPhenomenonType = weatherPhenomenonType;
        this.errorMessage = errorMessage;
    }
}
