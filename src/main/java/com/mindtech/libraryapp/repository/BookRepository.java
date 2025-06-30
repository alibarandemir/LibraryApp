package com.mindtech.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtech.libraryapp.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
