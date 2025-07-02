package com.mindtech.libraryapp.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Kitap adı boş olamaz")
    private String title;

    @NotNull(message = "Fiyat boş olamaz")
    @Positive(message = "Fiyat pozitif olmalıdır")
    private Double price;

    @JsonProperty("ISBN13")
    @NotBlank(message = "ISBN13 boş olamaz")
    @Size(min = 13, max = 13, message = "ISBN13 13 haneli olmalıdır")
    private String ISBN13;

    @NotNull(message = "Yayın tarihi boş olamaz")
    private LocalDate publicationDate;

    @NotBlank(message = "Yayınevi adı boş olamaz")
    private String publisherName;

    @NotBlank(message = "Yazar adı ve soyadı boş olamaz")
    private String authorNameSurname;
}
