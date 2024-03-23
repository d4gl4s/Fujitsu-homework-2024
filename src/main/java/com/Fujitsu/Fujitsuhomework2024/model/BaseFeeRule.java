package com.Fujitsu.Fujitsuhomework2024.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "base_fee_rules")
@Data
@RequiredArgsConstructor
public class BaseFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String vehicleType;

    @Column(nullable = false)
    private double fee;

    public BaseFeeRule(String city, String vehicleType, double fee) {
        this.city = city;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }
}

