package com.mindtech.libraryapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.AuthorDto;
import com.mindtech.libraryapp.service.AuthorService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    //Yazarları getirirken onlara ait kitapları da getiriyorum
    @GetMapping
    public ResponseEntity<ResponseDto<List<AuthorDto>>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
    
}
