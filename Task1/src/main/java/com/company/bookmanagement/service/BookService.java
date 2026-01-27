package com.company.bookmanagement.service;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse createBook(CreateBookRequest request);
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    BookResponse updateBook(Long id, UpdateBookRequest request);
    BookResponse deleteBook(Long id);
}