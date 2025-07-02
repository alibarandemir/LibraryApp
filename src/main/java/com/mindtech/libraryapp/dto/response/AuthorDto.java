package com.mindtech.libraryapp.dto.response;

import java.util.List;

import com.mindtech.libraryapp.model.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private String authorNameSurname;
    private List<BookDto> books;
}
