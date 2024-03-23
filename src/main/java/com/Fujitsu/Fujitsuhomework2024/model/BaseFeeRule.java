package com.Fujitsu.Fujitsuhomework2024.model;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
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
    private City city;

    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private double fee;

    public BaseFeeRule(City city, VehicleType vehicleType, double fee) {
        this.city = city;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }
}

