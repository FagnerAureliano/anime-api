package com.example.restapidevdojo.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
public class ExceptionDetails {
    public Instant timestamp;
    public Integer status;
    public String developerMessage;
    public String title;
    public String details;
}
