package com.mindtech.libraryapp.service.impl;

import org.springframework.stereotype.Service;

import com.mindtech.libraryapp.repository.BookRepository;
import com.mindtech.libraryapp.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    
}
