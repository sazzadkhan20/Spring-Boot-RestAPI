// src/main/java/com/company/bookmanagement/service/BookService.java
package com.company.bookmanagement.service;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;

import java.util.List;

/**
 * Book Service Interface
 *
 * Defines the business operations for book management.
 * This interface is the contract between controller and service implementation.
 *
 * DESIGN PATTERN: Interface Segregation
 * - Controllers depend on interface, not implementation
 * - Easy to mock for testing
 * - Allows multiple implementations
 */
public interface BookService {

    /**
     * Creates a new book
     *
     * @param request The book creation request
     * @return The created book response
     */
    BookResponse createBook(CreateBookRequest request);

    /**
     * Gets all books
     *
     * @return List of all books
     */
    List<BookResponse> getAllBooks();

    /**
     * Gets a book by ID
     *
     * @param id The book ID
     * @return The book response
     * @throws com.company.bookmanagement.exception.BookNotFoundException if book not found
     */
    BookResponse getBookById(Long id);

    /**
     * Updates an existing book
     *
     * @param id      The book ID to update
     * @param request The update request with new values
     * @return The updated book response
     * @throws com.company.bookmanagement.exception.BookNotFoundException if book not found
     */
    BookResponse updateBook(Long id, UpdateBookRequest request);

    /**
     * Deletes a book
     *
     * @param id The book ID to delete
     * @return The deleted book response
     * @throws com.company.bookmanagement.exception.BookNotFoundException if book not found
     */
    BookResponse deleteBook(Long id);
}