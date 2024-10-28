package com.votaciones.back.model.exception;

public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
