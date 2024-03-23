package com.Fujitsu.Fujitsuhomework2024.repository;

import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherObservation, Long> {

    WeatherObservation findFirstByStationNameOrderByTimestampDesc(String stationName);

}

