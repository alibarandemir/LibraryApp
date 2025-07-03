package com.mindtech.libraryapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.AuthorDto;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.exception.ErrorCodes;
import com.mindtech.libraryapp.model.Author;
import com.mindtech.libraryapp.repository.AuthorRepository;
import com.mindtech.libraryapp.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public ResponseDto<List<AuthorDto>> getAllAuthors() {
        try{
            List<Author> authors = authorRepository.findAll();
            List<AuthorDto> authorDtos = authors.stream().map(author -> {
                AuthorDto authorDto = new AuthorDto();
                authorDto.setAuthorNameSurname(author.getAuthorNameSurname());
                authorDto.setBooks(author.getBooks().stream().map(book -> {
                    return BookDto.builder()
                            .title(book.getTitle())
                            .ISBN13(book.getISBN13())
                            .publicationDate(book.getPublicationDate())
                            .price(book.getPrice())
                            .publisherName(book.getPublisher().getPublisherName())
                            .authorNameSurname(book.getAuthor().getAuthorNameSurname())
                            .build();
                }).toList());
                return authorDto;
            }).toList();
            return ResponseDto.successResponse(authorDtos, "Tüm yazarlar başarıyla getirildi");
        }
        catch(Exception e){
            e.printStackTrace();
            throw ErrorCodes.INTERNAL_SERVER_ERROR.exception(e.getMessage());
        }
       
    }
}
