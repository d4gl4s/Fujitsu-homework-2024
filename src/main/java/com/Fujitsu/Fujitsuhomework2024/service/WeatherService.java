package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.model.Observations;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import com.Fujitsu.Fujitsuhomework2024.repository.WeatherRepository;
import com.Fujitsu.Fujitsuhomework2024.util.UnixTimeToLocalDateTimeConverter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private static final Map<City, String> cityStationMap =  Map.of(
        City.TALLINN, "Tallinn-Harku",
        City.TARTU, "Tartu-Tõravere",
        City.PÄRNU, "Pärnu"
    );

    private static final String WEATHER_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final Set<String> OBSERVED_STATIONS = Set.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    private static final String CRON_EXPRESSION_DEFAULT = "0 15 * * * *"; // Runs every hour at HH:15:00

    @Scheduled(cron = CRON_EXPRESSION_DEFAULT) // Runs every hour at HH:15:00 by default
    public void importWeatherData() {
        try {
            String xmlData = restTemplate.getForObject(WEATHER_URL, String.class);
            parseAndInsertWeatherData(xmlData);
        } catch (Exception e) {
           throw new RuntimeException("Error importing weather data");
        }
    }

    private void parseAndInsertWeatherData(String xmlData) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Observations weatherList = xmlMapper.readValue(xmlData, Observations.class);
        for (WeatherObservation weatherObservation : weatherList.getStations()) {
            if(OBSERVED_STATIONS.contains(weatherObservation.getStationName())) saveWeatherData(weatherObservation, weatherList.getTimestamp());}
    }


    private void saveWeatherData(WeatherObservation weatherObservation, long timestampUnix) {
        LocalDateTime timestamp = UnixTimeToLocalDateTimeConverter.convertUnixTimestamp(timestampUnix);
        weatherObservation.setTimestamp(timestamp);
        weatherRepository.save(weatherObservation);
    }

    public WeatherObservation getWeatherAtDateTimeAtCity(LocalDateTime dateTime, City city) {
        Optional<WeatherObservation> observation;

        if (dateTime == null) // If dateTime is not provided, return the last observation
            observation = weatherRepository.findFirstByStationNameOrderByTimestampDesc(cityStationMap.get(city));
        else // If dateTime is provided, return the last observation before the given dateTime
            observation = weatherRepository.findFirstByStationNameAndTimestampBeforeOrderByTimestampDesc(cityStationMap.get(city), dateTime);

        return observation.orElseThrow(() -> new ResourceNotFoundException("Weather not found for given station"));
    }
}
