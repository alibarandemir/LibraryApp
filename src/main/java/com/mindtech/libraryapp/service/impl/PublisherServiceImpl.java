package com.mindtech.libraryapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.BookDto;
import com.mindtech.libraryapp.dto.response.PublisherDto;
import com.mindtech.libraryapp.exception.ErrorCodes;
import com.mindtech.libraryapp.model.Publisher;
import com.mindtech.libraryapp.repository.PublisherRepository;
import com.mindtech.libraryapp.service.PublisherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public ResponseDto<List<PublisherDto>> getAllPublishers() {
        try {
            List<Publisher> publishers = publisherRepository.findAll();
            List<PublisherDto> publisherDtos = publishers.stream().map(publisher -> {
                PublisherDto publisherDto = new PublisherDto();
                publisherDto.setPublisherName(publisher.getPublisherName());
                publisherDto.setBooks(publisher.getBooks().stream()
                        .map(book -> BookDto.builder()
                                .title(book.getTitle())
                                .ISBN13(book.getISBN13())
                                .price(book.getPrice())
                                .publisherName(publisher.getPublisherName())
                                .authorNameSurname(book.getAuthor().getAuthorNameSurname())
                                .build())
                        .collect(Collectors.toList()));
                return publisherDto;
            }).collect(Collectors.toList());
            
            return ResponseDto.successResponse(publisherDtos, "Tüm yayıncılar başarıyla getirildi");
        } catch (Exception e) {
            e.printStackTrace();
            throw ErrorCodes.INTERNAL_SERVER_ERROR.exception(e.getMessage());
        }
    }

    @Override
    public ResponseDto<List<PublisherDto>> getTwoRandomPublishers() {
        try {
            List<Publisher> publishers = publisherRepository.findAll();
            List<PublisherDto> publisherDtos = publishers.stream().map(publisher -> {
                PublisherDto publisherDto = new PublisherDto();
                publisherDto.setPublisherName(publisher.getPublisherName());
                publisherDto.setBooks(publisher.getBooks().stream()
                        .map(book -> BookDto.builder()
                                .title(book.getTitle())
                                .ISBN13(book.getISBN13())
                                .price(book.getPrice())
                                .publisherName(publisher.getPublisherName())
                                .authorNameSurname(book.getAuthor().getAuthorNameSurname())
                                .build())
                        .collect(Collectors.toList()));
                return publisherDto;
            }).limit(2).collect(Collectors.toList());
            
            return ResponseDto.successResponse(publisherDtos, "Tüm yayıncılar başarıyla getirildi");
        } catch (Exception e) {
            e.printStackTrace();
            throw ErrorCodes.INTERNAL_SERVER_ERROR.exception(e.getMessage());
        }
    }
}
