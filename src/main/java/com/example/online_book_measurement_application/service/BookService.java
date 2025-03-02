package com.example.online_book_measurement_application.service;

import com.example.online_book_measurement_application.dto.request.BookRequest;

public interface BookService {
    String createBook(BookRequest bookRequest );
}
