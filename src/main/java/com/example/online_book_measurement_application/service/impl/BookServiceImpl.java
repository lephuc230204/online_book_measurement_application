package com.example.online_book_measurement_application.service.impl;

import com.example.online_book_measurement_application.dto.request.BookRequest;
import com.example.online_book_measurement_application.entity.Author;
import com.example.online_book_measurement_application.entity.Book;
import com.example.online_book_measurement_application.repository.AuthorRepository;
import com.example.online_book_measurement_application.repository.BookRepository;
import com.example.online_book_measurement_application.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public String createBook(BookRequest bookRequest){
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found "));

        Book book = Book.builder()
                .bookName(bookRequest.getBookName())
                .author(author)
                .price(bookRequest.getPrice())
                .build();
        return "Success";
    }
}
