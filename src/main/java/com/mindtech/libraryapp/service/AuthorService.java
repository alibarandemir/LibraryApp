package com.mindtech.libraryapp.service;

import java.util.List;

import com.mindtech.libraryapp.dto.response.AuthorDto;
import com.mindtech.libraryapp.dto.ResponseDto;

public interface AuthorService {
    
    ResponseDto<List<AuthorDto>> getAllAuthors();


}
