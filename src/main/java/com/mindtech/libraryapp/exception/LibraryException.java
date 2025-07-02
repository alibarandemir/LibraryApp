package com.mindtech.libraryapp.exception;

import lombok.Getter;

@Getter
public class LibraryException extends RuntimeException {
    private final String message;
    private final int errorCode;

    public LibraryException(String message) {
        super(message);
        this.message = message;
        this.errorCode = 500;
    }

    public LibraryException(String message, int errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
} 