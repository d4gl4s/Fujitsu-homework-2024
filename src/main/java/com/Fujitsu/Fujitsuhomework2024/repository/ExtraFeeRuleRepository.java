package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ExtraFeeRuleRepository extends JpaRepository<ExtraFeeRule, Long> {
    List<ExtraFeeRule> findByVehicleType(VehicleType vehicleType);
    @Query("SELECT efr FROM ExtraFeeRule efr " +
            "WHERE (:dateTime IS NULL AND efr.endDate IS NULL AND :vehicleType IN efr.vehicleType) " +
            "OR (:dateTime IS NULL AND efr.endDate IS NOT NULL AND :vehicleType IN efr.vehicleType) " +
            "OR (:dateTime IS NOT NULL AND (:dateTime BETWEEN efr.startDate AND COALESCE(efr.endDate, :dateTime)) AND :vehicleType IN efr.vehicleType)")
    List<ExtraFeeRule> findByDateTimeAndVehicleType(@Param("dateTime") LocalDateTime dateTime, @Param("vehicleType") VehicleType vehicleType);
}

