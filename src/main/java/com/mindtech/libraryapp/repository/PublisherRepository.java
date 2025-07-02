package com.mindtech.libraryapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtech.libraryapp.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    
}
