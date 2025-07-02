package com.mindtech.libraryapp.model;

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
    private Integer price;

    @Column(name = "ISBN13",nullable = false,unique = true)
    private String ISBN13;

    //Kitap ile Yayınevi arasında 1-n ilişki var
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
    
}
