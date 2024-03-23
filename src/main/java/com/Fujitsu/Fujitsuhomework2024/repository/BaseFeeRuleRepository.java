package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BaseFeeRuleRepository extends JpaRepository<BaseFeeRule, Long> {
    @Query("SELECT bfr FROM BaseFeeRule bfr WHERE bfr.city = :city AND bfr.vehicleType = :vehicleType " +
            "AND (:dateTime IS NULL OR (:dateTime BETWEEN bfr.startDate AND COALESCE(bfr.endDate, :dateTime)))")
    BaseFeeRule findByCityAndVehicleTypeAndDateTime(@Param("city") City city,
                                                    @Param("vehicleType") VehicleType vehicleType,
                                                    @Param("dateTime") LocalDateTime dateTime);

}

