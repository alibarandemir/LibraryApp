package com.mindtech.libraryapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtech.libraryapp.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByAuthorNameSurname(String authorNameSurname);
}
