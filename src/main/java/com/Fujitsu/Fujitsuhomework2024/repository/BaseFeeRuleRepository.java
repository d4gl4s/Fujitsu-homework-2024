package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
    BaseFeeRule findByCityAndVehicleType(String city, String vehicleType);
}

