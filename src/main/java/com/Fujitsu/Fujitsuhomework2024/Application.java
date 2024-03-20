package com.Fujitsu.Fujitsuhomework2024;

import com.Fujitsu.Fujitsuhomework2024.model.ExtraFeeRule;
import com.Fujitsu.Fujitsuhomework2024.repository.ExtraFeeRuleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner run(ExtraFeeRuleRepository extraFeeRuleRepository){
		return args -> {
			// Create and save rules for ATEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule("Scooter", "temperature", "<-10", 1.0),
            new ExtraFeeRule("Scooter", "temperature", "-10-0", 0.5)
        ));

        // Create and save rules for WSEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule("Bike", "wind speed", "10-20", 0.5)
            // No need to save the forbidden scenario since it's not applicable for initialization
        ));

        // Create and save rules for WPEF
        extraFeeRuleRepository.saveAll(Arrays.asList(
            new ExtraFeeRule("Scooter", "weather phenomenon", "snow,sleet", 1.0),
            new ExtraFeeRule("Scooter", "weather phenomenon", "rain", 0.5)
        ));
		};
	}
}
