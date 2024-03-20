package com.Fujitsu.Fujitsuhomework2024.controller;

import com.Fujitsu.Fujitsuhomework2024.DTO.DeliveryFeeResponse;
import com.Fujitsu.Fujitsuhomework2024.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping("/calculateDeliveryFee")
    public ResponseEntity<DeliveryFeeResponse> calculateDeliveryFee(
            @RequestParam String city,
            @RequestParam String vehicleType
    ) {
        double deliveryFee = deliveryService.calculateDeliveryFee(city, vehicleType);
        DeliveryFeeResponse response = new DeliveryFeeResponse(deliveryFee);
        return ResponseEntity.ok(response);
    }
}
