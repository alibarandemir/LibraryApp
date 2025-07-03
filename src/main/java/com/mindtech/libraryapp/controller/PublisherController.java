package com.mindtech.libraryapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.PublisherDto;
import com.mindtech.libraryapp.service.PublisherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {
    
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<PublisherDto>>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }
    //Ben zaten tüm yayıncıları getirirken de kitapları ve yazarlarıyla birlikte getiriyorum
    //Burada sadece 2 tane rastgele publisher gelecek
    @GetMapping("/random")
    public ResponseEntity<ResponseDto<List<PublisherDto>>> getTwoRandomPublishers() {
        return ResponseEntity.ok(publisherService.getTwoRandomPublishers());
    }
}
