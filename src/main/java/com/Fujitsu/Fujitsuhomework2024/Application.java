package com.Fujitsu.Fujitsuhomework2024;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(ExtraFeeRuleRepository extraFeeRuleRepository){
		return args -> {

		String errorMessage = "Usage of selected vehicle type is forbidden";
			// Create and save rules for ATEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
			new ExtraFeeRule(Set.of("scooter", "bike"),"air temperature", null, -10D, 1),
			new ExtraFeeRule(Set.of("scooter", "bike"), "air temperature", -10D, 0D, 0.5)
        ));

        // Create and save rules for WSEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of("bike"),"wind speed", 10D, 20D, 0.5),
			new ExtraFeeRule(Set.of("bike"), "wind speed", 20D, null,errorMessage)
        ));

        // Create and save rules for WPEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("snow", "sleet"), 1),
			new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("rain"), 0.5),
			new ExtraFeeRule(Set.of("bike", "scooter"), "weather phenomenon", Set.of("glaze", "hail", "thunder"), errorMessage)
        ));
		};
	}
}
