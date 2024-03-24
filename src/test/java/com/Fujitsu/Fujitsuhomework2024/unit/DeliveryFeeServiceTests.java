package com.Fujitsu.Fujitsuhomework2024.unit;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.WeatherObservation;
import com.Fujitsu.Fujitsuhomework2024.service.DeliveryFeeService;
import com.Fujitsu.Fujitsuhomework2024.service.RuleService;
import com.Fujitsu.Fujitsuhomework2024.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DeliveryFeeServiceTests {

    @Mock
    private WeatherService weatherService;

    @Mock
    private RuleService ruleService;

    @InjectMocks
    @Autowired
    private DeliveryFeeService deliveryFeeService;
    private WeatherObservation sampleWeatherObservation;
    private final LocalDateTime localDateTime = LocalDateTime.now();

    @Before
    public void setUp(){
        // Mock base fee rules
        mockBaseFeeRules();
        //mockExtraFeeRules();
    }

    @Test
    public void testCalculateDeliveryFee_WithValidInput_ReturnsCorrectFee() {
        sampleWeatherObservation = new WeatherObservation(1L, "", "",10 , 5, "snow", LocalDateTime.now());
        // Mock behavior of weatherService and ruleService
        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(Mockito.any(LocalDateTime.class), Mockito.any(City.class)))
                .thenReturn(sampleWeatherObservation);


        // Call the method under test
        double deliveryFee = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER, null );
        // Assert the result
        assertEquals(3.0, deliveryFee, 0);
    }

     private void mockBaseFeeRules() {
         // Initialize map with city-vehicle combinations and base fee values
         Map<String, Integer> baseFeeMap = Map.of(
        "TALLINN_CAR", 3,
        "TALLINN_SCOOTER", 3,
        "TALLINN_BIKE", 3,
        "TARTU_CAR", 3,
        "TARTU_SCOOTER", 3,
        "TARTU_BIKE", 3,
        "PÄRNU_CAR", 3,
        "PÄRNU_SCOOTER", 3,
        "PÄRNU_BIKE", 3
        );

        // Mock the behavior of ruleService.getBaseFeeRuleByCityAndVehicleTypeAndDateTime()
        baseFeeMap.forEach((key, value) ->
            Mockito.lenient().when(ruleService.getBaseFeeRuleByCityAndVehicleTypeAndDateTime(getCityFromKey(key), getVehicleTypeFromKey(key), null))
                .thenReturn(new BaseFeeRule(getCityFromKey(key), getVehicleTypeFromKey(key), value))
        );
    }


    // Utility methods to extract city and vehicle type from the key
    private City getCityFromKey(String key) {
        return City.valueOf(key.split("_")[0]);
    }

    private VehicleType getVehicleTypeFromKey(String key) {
        return VehicleType.valueOf(key.split("_")[1]);
    }

    private void mockExtraFeeRules(){

        // Mock extra fee rules
        ExtraFeeRule airTemperature = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "air temperature", null, -10D, false, false, 1);
        ExtraFeeRule airTemperature1 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "air temperature", -10D, 0D, true, true, 25);


        ExtraFeeRule windSpeed = new ExtraFeeRule(Set.of(VehicleType.BIKE), "wind speed", 10D, 20D, true, true, 5);
        ExtraFeeRule windSpeed1 = new ExtraFeeRule(Set.of(VehicleType.BIKE), "wind speed", 20D, null, false, false, -1);

        ExtraFeeRule weatherPhenomenon = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("snow", "sleet"), 1);
        ExtraFeeRule weatherPhenomenon1 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("rain"), 65);
        ExtraFeeRule weatherPhenomenon2 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("glaze", "hail", "thunder"), -1);

        Mockito.lenient().when(ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType.SCOOTER, null))
                .thenReturn(List.of(airTemperature, airTemperature1, weatherPhenomenon, weatherPhenomenon1, weatherPhenomenon2 ));
        Mockito.lenient().when(ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType.BIKE, null))
                .thenReturn(List.of(airTemperature,airTemperature1, windSpeed, windSpeed1, weatherPhenomenon, weatherPhenomenon1, weatherPhenomenon2 ));
    }
}
