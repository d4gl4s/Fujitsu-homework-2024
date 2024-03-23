package com.Fujitsu.Fujitsuhomework2024;

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
			new BaseFeeRule("Tallinn", "car", 4),
			new BaseFeeRule("Tallinn", "scooter", 3.5),
			new BaseFeeRule("Tallinn", "bike", 3),
			new BaseFeeRule("Tartu", "car", 3.5),
			new BaseFeeRule("Tartu", "scooter", 3),
			new BaseFeeRule("Tartu", "bike", 2.5),
			new BaseFeeRule("Pärnu", "car", 3),
			new BaseFeeRule("Pärnu", "scooter", 2.5),
			new BaseFeeRule("Pärnu", "bike", 2)
		));

		// Create and save rules for ATEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
			new ExtraFeeRule(Set.of("scooter", "bike"),"air temperature", null, -10D,false,false, 1),
			new ExtraFeeRule(Set.of("scooter", "bike"), "air temperature", -10D, 0D,true, true, 0.5)
        ));

        // Create and save rules for WSEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of("bike"),"wind speed", 10D, 20D,true, true, 0.5),
			new ExtraFeeRule(Set.of("bike"), "wind speed", 20D, null,false, false, -1)
        ));

        // Create and save rules for WPEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("snow", "sleet"), 1),
			new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("rain"), 0.5),
			new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("glaze", "hail", "thunder"), -1)
        ));

		weatherService.importWeatherData();
		};
	}
}
