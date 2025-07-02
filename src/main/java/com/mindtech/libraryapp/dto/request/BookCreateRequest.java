package com.mindtech.libraryapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {
    private String title;
    private Double price;
    private String ISBN13;
    private String publisherName;
    private String authorNameSurname;
}
