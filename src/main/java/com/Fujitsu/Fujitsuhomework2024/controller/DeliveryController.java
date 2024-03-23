package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.DTO.DeliveryFeeResponse;
import com.Fujitsu.Fujitsuhomework2024.enums.City;
import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import com.Fujitsu.Fujitsuhomework2024.service.DeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class DeliveryController {
    private final DeliveryFeeService deliveryService;

    @GetMapping("/calculateDeliveryFee")
    public ResponseEntity<Double> calculateDeliveryFee(
            @RequestParam City city,
            @RequestParam VehicleType vehicleType,
            @RequestParam(required = false) LocalDateTime dateTime
    ) {
        double deliveryFee = deliveryService.calculateDeliveryFee(city, vehicleType, dateTime);
        return ResponseEntity.ok(deliveryFee);
    }
}
