package com.example.week8_day4.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String message) {
        super("Tag " + message + " already exists for this task");
    }
}
