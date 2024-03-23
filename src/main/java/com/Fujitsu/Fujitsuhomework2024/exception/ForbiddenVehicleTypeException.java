package com.Fujitsu.Fujitsuhomework2024.exception;

public class ForbiddenVehicleTypeException extends RuntimeException{
    public ForbiddenVehicleTypeException() {
        super("Usage of selected vehicle type is forbidden");
    }
    public ForbiddenVehicleTypeException(String message) {
        super(message);
    }
}
