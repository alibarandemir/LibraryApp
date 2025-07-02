package com.mindtech.libraryapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtech.libraryapp.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @Query(value = "SELECT * FROM books WHERE EXTRACT(YEAR FROM publication_date) > 2023", nativeQuery = true)
    List<Book> findBooksAfter2023();
}
