package com.mindtech.libraryapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private String title;
    private Double price;
    private String ISBN13;
    private String publisherName;
    private String authorNameSurname;
}
