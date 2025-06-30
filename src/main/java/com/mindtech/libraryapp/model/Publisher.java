package com.mindtech.libraryapp.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "publishers")
@AttributeOverride(name = "id", column = @Column(name = "publisherId"))
public class Publisher extends BaseEntity {

    @Column(name = "publisherName",nullable = false)
    private String publisherName;


}
    