package com.mindtech.libraryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mindtech.libraryapp.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Domain özelinde exception
    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<ResponseDto<Void>> handleLibraryException(LibraryException ex) {
        ResponseDto<Void> response = ResponseDto.errorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode()));
    }

    //Genel exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleGenericException(Exception ex) {
        ex.printStackTrace();
        ResponseDto<Void> response = ResponseDto.errorResponse("Beklenmeyen bir hata oluştu: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 