package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExtraFeeRuleRepository extends JpaRepository<ExtraFeeRule, Long> {

    /**
     * Finds the extra fee rules applicable for a specific date/time and vehicle type.
     *
     * @param dateTime     The date and time for which the extra fee rules are applicable.
     * @param vehicleType  The type of vehicle for which the extra fee rules are applicable.
     * @return A {@link List} of {@link ExtraFeeRule} objects representing the applicable extra fee rules.
     */
    @Query("SELECT efr FROM ExtraFeeRule efr " +
            "WHERE (:dateTime IS NULL AND efr.endDate IS NULL AND :vehicleType MEMBER OF efr.vehicleType) " +
            "OR (:dateTime IS NULL AND efr.endDate IS NOT NULL AND :vehicleType MEMBER OF efr.vehicleType) " +
            "OR (:dateTime IS NOT NULL AND (:dateTime BETWEEN efr.startDate AND COALESCE(efr.endDate, :dateTime)) AND :vehicleType MEMBER OF efr.vehicleType)")
    List<ExtraFeeRule> findByDateTimeAndVehicleType(@Param("dateTime") LocalDateTime dateTime,
                                                    @Param("vehicleType") VehicleType vehicleType);


}


