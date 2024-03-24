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

    /**
     * Finds the base fee rule for a specific city, vehicle type.
     * If dateTime is provided, will return base rule that was applied at given date, otherwise returns the current rule.
     *
     * @param city       The city for which to find the base fee rule.
     * @param vehicleType The type of vehicle for which to find the base fee rule.
     * @param dateTime   The date and time for which to find the base fee rule (optional).
     * @return An {@link Optional} containing the {@link BaseFeeRule} for the specified city, vehicle type,
     *         and date/time, or empty if no rule is found.
     */
    @Query("SELECT bfr FROM BaseFeeRule bfr " +
       "WHERE bfr.city = :city AND bfr.vehicleType = :vehicleType " +
       "AND (:dateTime IS NULL OR (:dateTime BETWEEN bfr.startDate AND COALESCE(bfr.endDate, :dateTime))) " +
       "ORDER BY bfr.startDate DESC " +
       "LIMIT 1")
    Optional<BaseFeeRule> findByCityAndVehicleTypeAndDateTime(@Param("city") City city,
                                                              @Param("vehicleType") VehicleType vehicleType,
                                                              @Param("dateTime") LocalDateTime dateTime);

}

