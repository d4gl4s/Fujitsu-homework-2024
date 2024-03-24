package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherObservation, Long> {

    /**
     * Finds the latest weather observation recorded for a specific station.
     *
     * @param stationName The name of the weather station to find the latest observation for.
     * @return An {@link Optional} containing the latest {@link WeatherObservation} for the specified station,
     *         or empty if no observation is found.
     */
    Optional<WeatherObservation> findFirstByStationNameOrderByTimestampDesc(String stationName);


    /**
     * Finds the latest weather observation recorded for a specific station before a given timestamp.
     *
     * @param stationName The name of the weather station to find the latest observation for.
     * @param dateTime     The timestamp before which the observation should have occurred.
     * @return An {@link Optional} containing the latest {@link WeatherObservation} for the specified station
     *         before the given timestamp, or empty if no observation is found.
     */
    Optional<WeatherObservation> findFirstByStationNameAndTimestampBeforeOrderByTimestampDesc(String stationName, LocalDateTime dateTime);

}

