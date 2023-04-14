package com.rbaliwal00.todoappusingjsp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private String timestamp;
    private String message;
    private String details;
}
