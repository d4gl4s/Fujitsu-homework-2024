package com.Fujitsu.Fujitsuhomework2024.service;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CronJobScheduler {
    private final WeatherImportService weatherImportService;

    @Scheduled(cron = "0 15 * * * *") // Cron expression for running the job every hour at 15 minutes past the hour
    public void importWeatherDataScheduled() {
        weatherImportService.importWeatherData();
    }
}
