package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherObservation, Long> {

    Optional<WeatherObservation> findFirstByStationNameOrderByTimestampDesc(String stationName);

    Optional<WeatherObservation> findFirstByStationNameAndTimestampBeforeOrderByTimestampDesc(String s, LocalDateTime dateTime);
}

