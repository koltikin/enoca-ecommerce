package com.enoca.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AnyError {
    private boolean success;
    private String errorMassage;
    private String uri;
    private LocalDateTime dateTime;

    public AnyError(String errorMassage, String uri, LocalDateTime dateTime) {
        this.success = false;
        this.errorMassage = errorMassage;
        this.uri = uri;
        this.dateTime = dateTime;
    }
}
