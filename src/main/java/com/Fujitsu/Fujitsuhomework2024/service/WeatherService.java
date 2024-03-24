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
    private static final String WEATHER_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php"; // URL for fetching weather data
    private static final Set<String> OBSERVED_STATIONS = Set.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    private static final String CRON_EXPRESSION_DEFAULT = "0 15 * * * *"; // Default cron expression for scheduled weather data import, rund every hour at HH:15

    // Mapping between city and observation station name
    private static final Map<City, String> cityStationMap =  Map.of(
        City.TALLINN, "Tallinn-Harku",
        City.TARTU, "Tartu-Tõravere",
        City.PÄRNU, "Pärnu"
    );

    /**
     * Scheduled method to import weather data from the external API.
     */
    @Scheduled(cron = CRON_EXPRESSION_DEFAULT) // Runs every hour at HH:15:00 by default
    public void importWeatherData() {
        try {
            String xmlData = restTemplate.getForObject(WEATHER_URL, String.class);
            parseAndInsertWeatherData(xmlData);
        } catch (Exception e) {
           throw new RuntimeException("Error importing weather data");
        }
    }


    // Parses the XML weather data and inserts it into the database.
    private void parseAndInsertWeatherData(String xmlData) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Observations weatherList = xmlMapper.readValue(xmlData, Observations.class); // Map xml data to Java classes
        for (WeatherObservation weatherObservation : weatherList.getStations()) { // Save observation data for relevant stations
            if(OBSERVED_STATIONS.contains(weatherObservation.getStationName())) saveWeatherData(weatherObservation, weatherList.getTimestamp());
        }
    }

    // Saves weather data into the database.
    private void saveWeatherData(WeatherObservation weatherObservation, long timestampUnix) {
        LocalDateTime timestamp = UnixTimeToLocalDateTimeConverter.convertUnixTimestamp(timestampUnix);
        weatherObservation.setTimestamp(timestamp);
        weatherRepository.save(weatherObservation);
    }


    /**
     * Retrieves the weather observation for a specific city at a given date and time.
     *
     * @param dateTime The date and time for which to retrieve the weather observation.
     * @param city     The city for which to retrieve the weather observation.
     * @return The weather observation for the specified city at the given date and time.
     * @throws ResourceNotFoundException if no weather observation is found for the given city and time.
     */
    public WeatherObservation getWeatherAtDateTimeAtCity(LocalDateTime dateTime, City city) {
        Optional<WeatherObservation> observation;

        if (dateTime == null) // If dateTime is not provided, return the last observation
            observation = weatherRepository.findFirstByStationNameOrderByTimestampDesc(cityStationMap.get(city));
        else // If dateTime is provided, return the last observation before the given dateTime
            observation = weatherRepository.findFirstByStationNameAndTimestampBeforeOrderByTimestampDesc(cityStationMap.get(city), dateTime);

        return observation.orElseThrow(() -> new ResourceNotFoundException("Weather not found for given station"));
    }
}
