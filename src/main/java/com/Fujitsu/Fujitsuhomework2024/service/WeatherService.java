package com.Fujitsu.Fujitsuhomework2024.service;

import com.Fujitsu.Fujitsuhomework2024.model.Observations;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import com.Fujitsu.Fujitsuhomework2024.repository.WeatherRepository;
import com.Fujitsu.Fujitsuhomework2024.util.UnixTimeToLocalDateTimeConverter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String WEATHER_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final Set<String> OBSERVED_STATIONS = Set.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    private static final String CRON_EXPRESSION_DEFAULT = "0 15 * * * *"; // Runs every hour at HH:15:00

    @Scheduled(cron = CRON_EXPRESSION_DEFAULT) // Runs every hour at HH:15:00 by default
    public void importWeatherData() {
        try {
            String xmlData = restTemplate.getForObject(WEATHER_URL, String.class);
            parseAndInsertWeatherData(xmlData);
        } catch (Exception e) {
            System.out.println("Error importing weather data");
            //log.error("Error occurred while importing weather data: {}", e.getMessage());
        }
    }

    private void parseAndInsertWeatherData(String xmlData) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            Observations weatherList = xmlMapper.readValue(xmlData, Observations.class);
            for (WeatherObservation weatherObservation : weatherList.getStations()) {
                if(OBSERVED_STATIONS.contains(weatherObservation.getStationName())) saveWeatherData(weatherObservation, weatherList.getTimestamp());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //log.error("Error occurred while parsing and inserting weather data: {}", e.getMessage());
        }
    }


    private void saveWeatherData(WeatherObservation weatherObservation, long timestampUnix) {
        try {
            LocalDateTime timestamp = UnixTimeToLocalDateTimeConverter.convertUnixTimestamp(timestampUnix);
            weatherObservation.setTimestamp(timestamp);
            weatherRepository.save(weatherObservation);
        } catch (Exception e) {
            System.out.println("Error while saving weather data.");
            //log.error("Error occurred while saving weather data: {}", e.getMessage());
        }
    }
}
