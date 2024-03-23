package com.Fujitsu.Fujitsuhomework2024.config;

import com.Fujitsu.Fujitsuhomework2024.util.LowercaseEnumConverter;
import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LowercaseEnumConverter<>(City.class));
        registry.addConverter(new LowercaseEnumConverter<>(VehicleType.class));
    }
}
