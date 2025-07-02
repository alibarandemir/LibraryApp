package com.mindtech.libraryapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreateRequest {
    @NotBlank(message = "Kitap adı boş bırakılamaz")
    private String title;

    @NotNull(message = "Fiyat boş bırakılamaz")
    private Double price;

    @Size(min = 13, max = 13, message = "ISBN13 13 haneli olmalıdır")
    @NotBlank(message = "ISBN13 boş bırakılamaz")
    private String ISBN13;

    @NotBlank(message = "Yayınevi adı boş bırakılamaz")
    private String publisherName;
    private String authorNameSurname;
}
