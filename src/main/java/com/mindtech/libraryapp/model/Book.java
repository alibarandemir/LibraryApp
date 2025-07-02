package com.mindtech.libraryapp.model;

import java.time.LocalDate;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
@AttributeOverride(name = "id", column = @Column(name = "bookId"))
public class Book extends BaseEntity {

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "price")
    private Double price;

    //Bu attribute ödevdeki model örneğinde yoktu fakat casede istenilen diğer alan için gerekliydi
    @Column(name = "publication_date",nullable = false)
    private LocalDate publicationDate;

    @Column(name = "ISBN13",nullable = false,unique = true)
    private String ISBN13;

    //Kitap ile Yayınevi arasında 1-n ilişki var
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    
}
