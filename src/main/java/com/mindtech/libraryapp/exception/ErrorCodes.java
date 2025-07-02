package com.mindtech.libraryapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes {
    BOOK_NOT_FOUND(404, "Kitap bulunamadı"),
    BOOK_ALREADY_EXISTS(409, "Bu kitap zaten mevcut"),
    INVALID_BOOK_DATA(400, "Geçersiz kitap bilgisi"),
    AUTHOR_NOT_FOUND(404, "Yazar bulunamadı"),
    PUBLISHER_NOT_FOUND(404, "Yayınevi bulunamadı"),
    INTERNAL_SERVER_ERROR(500, "Sunucu hatası");
    

    private final int code;
    private final String message;

    public LibraryException exception() {
        return new LibraryException(this.message, this.code);
    }

    public LibraryException exception(String customMessage) {
        return new LibraryException(customMessage, this.code);
    }
} 