package com.enoca.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerError {
    private boolean success;
    private String errorMassage;

    public InternalServerError(String errorMassage) {
        this.success = false;
        this.errorMassage = errorMassage;
    }
}
