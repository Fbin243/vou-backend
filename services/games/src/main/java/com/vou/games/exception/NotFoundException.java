package com.vou.games.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
