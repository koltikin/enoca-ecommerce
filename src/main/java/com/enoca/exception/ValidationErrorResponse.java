package com.enoca.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationErrorResponse {
    private boolean success;
    private String field;
    private String message;

    public ValidationErrorResponse(String field, String message) {
        this.success = false;
        this.field = field;
        this.message = message;
    }
}