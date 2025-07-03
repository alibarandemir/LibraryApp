package com.mindtech.libraryapp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mindtech.libraryapp.dto.response.GoogleBookResponse;



@FeignClient(name = "googleBooks", url = "https://www.googleapis.com")
public interface GoogleBooksClient {
    
    @GetMapping("/books/v1/volumes")
    public GoogleBookResponse searchBooks(@RequestParam("q") String query);
}
