package com.example.productfinder;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String message) {
        super(message);
    }
}
