package com.Fujitsu.Fujitsuhomework2024.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String vehicleType;

    @Column(nullable = false)
    private String condition; // e.g., temperature, wind speed, weather phenomenon

    @Column(nullable = false)
    private String conditionValue; // e.g., temperature range, wind speed range, weather phenomenon type

    @Column(nullable = false)
    private double fee;

    public ExtraFeeRule(String vehicleType, String condition, String conditionValue, double fee) {
        this.vehicleType = vehicleType;
        this.condition = condition;
        this.conditionValue = conditionValue;
        this.fee = fee;
    }
}
