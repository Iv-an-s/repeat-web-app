package com.geekbrains.repeatapp.exceptions;

import lombok.Data;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
