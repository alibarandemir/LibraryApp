package com.mindtech.libraryapp.service;

import java.util.List;

import com.mindtech.libraryapp.dto.ResponseDto;
import com.mindtech.libraryapp.dto.response.PublisherDto;

public interface PublisherService {
    
    ResponseDto<List<PublisherDto>> getAllPublishers();
    ResponseDto<List<PublisherDto>> getTwoRandomPublishers();

}
