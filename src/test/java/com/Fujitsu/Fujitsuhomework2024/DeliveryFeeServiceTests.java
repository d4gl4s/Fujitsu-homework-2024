package com.Fujitsu.Fujitsuhomework2024;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.exception.ForbiddenVehicleTypeException;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Before
    public void setUp(){
        // Mock rules
        mockBaseFeeRules();
        mockExtraFeeRules();
    }

    @Test
    public void testCalculateFee_WithValidInput_NoExtraFee() {
        WeatherObservation sampleWeatherObservation = new WeatherObservation();
        sampleWeatherObservation.setAirTemperature(5);
        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleWeatherObservation);
        // 1-way testing for inputs
        double deliveryFeeTartu = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER, null );
        double deliveryFeeTallinn = deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.CAR, null );
        double deliveryFeePärnu = deliveryFeeService.calculateDeliveryFee(City.PÄRNU, VehicleType.BIKE, null );
        assertEquals(3.0, deliveryFeeTartu, 0);
        assertEquals(4.0, deliveryFeeTallinn, 0);
        assertEquals(2.0, deliveryFeePärnu, 0);
    }

    @Test
    public void testCalculateFee_WithValidInput_ExtraFee() {
        WeatherObservation sampleObservation1 = new WeatherObservation(-15, 0.0, "Light snowfall");
        WeatherObservation sampleObservation2 = new WeatherObservation(-5, 15, "light sleet");
        WeatherObservation sampleObservation3 = new WeatherObservation(2, 5, "LIGHT RAIN");

        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservation1);
        double deliveryFee1 = deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER, null );
        assertEquals(5, deliveryFee1, 0);

        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservation2);
        double deliveryFee2 = deliveryFeeService.calculateDeliveryFee(City.TALLINN, VehicleType.BIKE, null );
        assertEquals(5, deliveryFee2, 0);

        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservation3);
        double deliveryFee3 = deliveryFeeService.calculateDeliveryFee(City.PÄRNU, VehicleType.BIKE, null );
        assertEquals(2.5, deliveryFee3, 0);

        // Checking that extra rules do not apply to car vehicle type, since there are no extra fee rules for cars.
        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservation1);
        double deliveryFee4 = deliveryFeeService.calculateDeliveryFee(City.PÄRNU, VehicleType.CAR, null );
        assertEquals(3, deliveryFee4, 0);
    }

    @Test
    public void testCalculateFee_WithValidInput_ExtraFee_Error() {
        WeatherObservation sampleObservationHighWind = new WeatherObservation(2, 30, null); // Should throw error for extreme wind
        WeatherObservation sampleObservationExtremeWeatherPhenomenon = new WeatherObservation(5, 15, "Glaze"); // Should throw error for extreme weather phenomenon

        // Checking extra fee rules, that should throw predefined error
        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservationHighWind);
        ForbiddenVehicleTypeException exceptionHighWindSpeed = assertThrows(ForbiddenVehicleTypeException.class, () ->
                deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.BIKE, null));

        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(any(), Mockito.any(City.class)))
                .thenReturn(sampleObservationExtremeWeatherPhenomenon);
        ForbiddenVehicleTypeException exceptionExtremeWeatherPhenomenon = assertThrows(ForbiddenVehicleTypeException.class, () ->
                deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER, null));

        assert exceptionHighWindSpeed.getMessage().contains("Usage of selected vehicle type is forbidden");
        assert exceptionExtremeWeatherPhenomenon.getMessage().contains("Usage of selected vehicle type is forbidden");
    }

    @Test
    public void calculateFee_WithInvalidInput_DateInFuture() {
        createEmptySampleObservation();
        LocalDateTime nextWeekDateTime = LocalDateTime.now().plusWeeks(1);// Add one week
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deliveryFeeService.calculateDeliveryFee(City.TARTU, VehicleType.SCOOTER, nextWeekDateTime));
        assert exception.getMessage().contains("Date cannot be in the future");
    }

    @Test
    public void calculateFee_WithInvalidInput_NullCity() {
        createEmptySampleObservation();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deliveryFeeService.calculateDeliveryFee(null, VehicleType.SCOOTER, null));
        assert exception.getMessage().contains("City and vehicle type must not be null");
    }

    @Test
    public void calculateFee_WithInvalidInput_NullVehicleType() {
        createEmptySampleObservation();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deliveryFeeService.calculateDeliveryFee(City.TARTU,null, null));
        assert exception.getMessage().contains("City and vehicle type must not be null");
    }

     private void mockBaseFeeRules() {
         // Initialize map with city-vehicle combinations and base fee values
         Map<String, Double> baseFeeMap = Map.of(
        "TALLINN_CAR", 4D,
        "TALLINN_SCOOTER", 3.5,
        "TALLINN_BIKE", 3D,
        "TARTU_CAR", 3.5,
        "TARTU_SCOOTER", 3D,
        "TARTU_BIKE", 2.5,
        "PÄRNU_CAR", 3D,
        "PÄRNU_SCOOTER", 2.5,
        "PÄRNU_BIKE", 2D
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
        Set<String> snowAndSleetPhenomenon = Set.of("Light snow shower", "Moderate snow shower", "Heavy snow shower", "Light snowfall", "Moderate snowfall", "Heavy snowfall", "Blowing snow", "Drifting snow", "Light sleet", "Moderate sleet");
        Set<String> rainPhenomenon = Set.of("Light shower", "Moderate shower", "Heavy shower", "Light rain", "Moderate rain", "Heavy rain", "Thunderstorm");

        // Mock extra fee rules
        ExtraFeeRule airTemperature = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "air temperature", null, -10D, false, false, 1);
        ExtraFeeRule airTemperature1 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "air temperature", -10D, 0D, true, true, 0.5);



        ExtraFeeRule windSpeed = new ExtraFeeRule(Set.of(VehicleType.BIKE), "wind speed", 10D, 20D, true, true, 0.5);
        ExtraFeeRule windSpeed1 = new ExtraFeeRule(Set.of(VehicleType.BIKE), "wind speed", 20D, null, false, false, -1);

        ExtraFeeRule weatherPhenomenon = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon",snowAndSleetPhenomenon, 1);
        ExtraFeeRule weatherPhenomenon1 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", rainPhenomenon, 0.5);
        ExtraFeeRule weatherPhenomenon2 = new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("Glaze", "Hail", "Thunder"), -1);

        Mockito.lenient().when(ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType.SCOOTER, null))
                .thenReturn(List.of(airTemperature, airTemperature1, weatherPhenomenon, weatherPhenomenon1, weatherPhenomenon2 ));
        Mockito.lenient().when(ruleService.findExtraFeeRulesByVehicleTypeAndDateTime(VehicleType.BIKE, null))
                .thenReturn(List.of(airTemperature,airTemperature1, windSpeed, windSpeed1, weatherPhenomenon, weatherPhenomenon1, weatherPhenomenon2 ));
    }

    private void createEmptySampleObservation(){
        WeatherObservation sampleWeatherObservation = new WeatherObservation();
        Mockito.lenient().when(weatherService.getWeatherAtDateTimeAtCity(Mockito.any(LocalDateTime.class), Mockito.any(City.class)))
                .thenReturn(sampleWeatherObservation);
    }
}
