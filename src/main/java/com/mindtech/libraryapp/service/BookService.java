package com.mindtech.libraryapp.service;

import com.mindtech.libraryapp.dto.response.BookDto;

import java.util.List;

import com.mindtech.libraryapp.dto.request.BookCreateRequest;

public interface BookService {
    //crud işlemleri
    BookDto createBook(BookCreateRequest request);
    BookDto getBookById(Long id);
    List<BookDto> getAllBooks();
    BookDto updateBook(Long id, BookCreateRequest request);
    void deleteBook(Long id);

    //A ile başlayan kitapları getiren servis imzası
    List<BookDto> getBooksStartingWithA();
    //2023 yılından sonra yayınlanan kitapları getiren servis imzası
    List<BookDto> getBooksAfter2023();


    
} 
