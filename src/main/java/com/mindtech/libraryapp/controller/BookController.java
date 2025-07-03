package com.mindtech.libraryapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.request.BookCreateRequest;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<BookDto>>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity<ResponseDto<BookDto>> createBook(@Valid @RequestBody BookCreateRequest request){
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<BookDto>> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<BookDto>> updateBook(@PathVariable Long id, @Valid @RequestBody BookCreateRequest request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/a-books")
    public ResponseEntity<ResponseDto<List<BookDto>>> getBooksStartingWithA(){
        return ResponseEntity.ok(bookService.getBooksStartingWithA());
    }
    //Normalde istekten herhangi bir yıl parametresi de alabilirdim casede 2023 dendiği için dışına çıkmak istemedim
    @GetMapping("/after-2023")
    public ResponseEntity<ResponseDto<List<BookDto>>> getBooksAfter2023(){
        return ResponseEntity.ok(bookService.getBooksAfter2023());
    }

    //Swagger Dokümantasyon için
    @Operation(summary = "Search books from Google API")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<List<BookDto>>> searchBooks(@RequestParam("q") String query){
        return ResponseEntity.ok(bookService.searchBooks(query));
    }
}
