package com.Fujitsu.Fujitsuhomework2024.config;

import com.Fujitsu.Fujitsuhomework2024.util.CityEnumConverter;
import com.Fujitsu.Fujitsuhomework2024.util.VehicleTypeEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CityEnumConverter());
        registry.addConverter(new VehicleTypeEnumConverter());
    }
}
