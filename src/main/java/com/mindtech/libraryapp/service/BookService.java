package com.mindtech.libraryapp.service;

import java.util.List;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.request.BookCreateRequest;
import com.mindtech.libraryapp.dto.response.BookDto;

public interface BookService {
    //crud işlemleri
    ResponseDto<BookDto> createBook(BookCreateRequest request);
    ResponseDto<BookDto> getBookById(Long id);
    ResponseDto<List<BookDto>> getAllBooks();
    ResponseDto<BookDto> updateBook(Long id, BookCreateRequest request);
    ResponseDto<Void> deleteBook(Long id);

    //A ile başlayan kitapları getiren servis imzası
    ResponseDto<List<BookDto>> getBooksStartingWithA();
    //2023 yılından sonra yayınlanan kitapları getiren servis imzası
    ResponseDto<List<BookDto>> getBooksAfter2023();

    ResponseDto<List<BookDto>> searchBooks(String query);


    
} 
