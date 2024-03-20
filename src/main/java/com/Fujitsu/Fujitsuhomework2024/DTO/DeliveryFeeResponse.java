package com.Fujitsu.Fujitsuhomework2024.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryFeeResponse {
    private double totalDeliveryFee;
    private String errorMessage;

    public DeliveryFeeResponse(double totalDeliveryFee) {
        this.totalDeliveryFee = totalDeliveryFee;
    }

    public DeliveryFeeResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
