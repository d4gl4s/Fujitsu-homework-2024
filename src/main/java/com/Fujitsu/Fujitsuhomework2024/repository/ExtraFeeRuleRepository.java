package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraFeeRuleRepository extends JpaRepository<ExtraFeeRule, Long> {
    List<ExtraFeeRule> findByVehicleType(String vehicleType);
}

