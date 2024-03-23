package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
    BaseFeeRule findByCityAndVehicleType(City city, VehicleType vehicleType);
}

