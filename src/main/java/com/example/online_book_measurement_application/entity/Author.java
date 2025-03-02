package com.example.online_book_measurement_application.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table( name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long authorId;

    private String authorName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;

    private String address;

}
