package com.Fujitsu.Fujitsuhomework2024.controller;

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


    /**
     * Calculates the delivery fee based on the given city, vehicle type, and optional date and time.
     *
     * @param city       The city where the delivery is to be made.
     * @param vehicleType The type of vehicle to be used for the delivery.
     * @param dateTime   The date and time of the delivery (optional).
     * @return A {@link ResponseEntity} containing the calculated delivery fee as a {@link Double} or an error in case of failure.
     */
    @GetMapping("/api/calculateDeliveryFee")
    public ResponseEntity<Double> calculateDeliveryFee(
            @RequestParam City city,
            @RequestParam VehicleType vehicleType,
            @RequestParam(required = false) LocalDateTime dateTime
    ) {
        double deliveryFee = deliveryService.calculateDeliveryFee(city, vehicleType, dateTime);
        return ResponseEntity.ok(deliveryFee);
    }

}
