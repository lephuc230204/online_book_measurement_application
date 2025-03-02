package com.example.online_book_measurement_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="author_id")
    private Author author;

    private double price; // free or price


}
