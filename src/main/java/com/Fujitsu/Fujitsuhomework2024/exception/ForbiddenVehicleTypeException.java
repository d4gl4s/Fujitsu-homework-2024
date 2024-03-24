package com.Fujitsu.Fujitsuhomework2024.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForbiddenVehicleTypeException extends RuntimeException{
    private final String message;

    @Override
    public String getMessage() {return message;}
}
