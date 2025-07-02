package com.mindtech.libraryapp.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookDto {
    private String title;
    private Double price;
    @JsonProperty("ISBN13")
    private String ISBN13;
    private LocalDate publicationDate;
    private String publisherName;
    private String authorNameSurname;
}
