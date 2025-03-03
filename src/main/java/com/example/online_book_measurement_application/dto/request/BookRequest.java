package com.example.online_book_measurement_application.dto.request;

import com.example.online_book_measurement_application.entity.Author;
import lombok.Data;

@Data
public class BookRequest {

    private String bookName;

    private Long authorId;

    private double price;

}
