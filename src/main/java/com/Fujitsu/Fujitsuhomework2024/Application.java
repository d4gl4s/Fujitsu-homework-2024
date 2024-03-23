package com.Fujitsu.Fujitsuhomework2024;

import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.model.BaseFeeRule;
import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.BaseFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import com.Fujitsu.Fujitsuhomework2024.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(BaseFeeRuleRepository baseFeeRuleRepository, ExtraFeeRuleRepository extraFeeRuleRepository, WeatherService weatherService){
		return args -> {

		baseFeeRuleRepository.saveAll(Arrays.asList(
			new BaseFeeRule(City.TALLINN, VehicleType.CAR, 4),
			new BaseFeeRule(City.TALLINN, VehicleType.SCOOTER, 3.5),
			new BaseFeeRule(City.TALLINN, VehicleType.BIKE, 3),
			new BaseFeeRule(City.TARTU, VehicleType.CAR, 3.5),
			new BaseFeeRule(City.TARTU, VehicleType.SCOOTER, 3),
			new BaseFeeRule(City.TARTU, VehicleType.BIKE, 2.5),
			new BaseFeeRule(City.PÄRNU, VehicleType.CAR, 3),
			new BaseFeeRule(City.PÄRNU, VehicleType.SCOOTER, 2.5),
			new BaseFeeRule(City.PÄRNU, VehicleType.BIKE, 2)
		));

		// Create and save rules for ATEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
			new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE),"air temperature", null, -10D,false,false, 1),
			new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "air temperature", -10D, 0D,true, true, 0.5)
        ));

        // Create and save rules for WSEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of(VehicleType.BIKE),"wind speed", 10D, 20D,true, true, 0.5),
			new ExtraFeeRule(Set.of(VehicleType.BIKE), "wind speed", 20D, null,false, false, -1)
        ));

        // Create and save rules for WPEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("snow", "sleet"), 1),
			new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("rain"), 0.5),
			new ExtraFeeRule(Set.of(VehicleType.SCOOTER, VehicleType.BIKE), "weather phenomenon", Set.of("glaze", "hail", "thunder"), -1)
        ));

		weatherService.importWeatherData();
		};
	}
}
