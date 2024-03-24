package com.Fujitsu.Fujitsuhomework2024.exception;


public class ResourceNotFoundException extends RuntimeException{
    private final String message;

    public ResourceNotFoundException(String message) {
       this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
