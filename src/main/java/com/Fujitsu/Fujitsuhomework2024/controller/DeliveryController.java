package com.Fujitsu.Fujitsuhomework2024.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeliveryController {
   /* private final DeliveryFeeService deliveryService;

    @GetMapping("/calculateDeliveryFee")
    public ResponseEntity<Double> calculateDeliveryFee(
            @RequestParam City city,
            @RequestParam VehicleType vehicleType,
            @RequestParam(required = false) LocalDateTime dateTime
    ) {
        double deliveryFee = deliveryService.calculateDeliveryFee(city, vehicleType, dateTime);
        return ResponseEntity.ok(deliveryFee);
    }*/
}
