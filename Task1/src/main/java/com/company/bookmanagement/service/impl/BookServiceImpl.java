// src/main/java/com/company/bookmanagement/service/impl/BookServiceImpl.java
package com.company.bookmanagement.service.impl;

import com.company.bookmanagement.exception.BookNotFoundException;
import com.company.bookmanagement.mapper.BookMapper;
import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.model.entity.Book;
import com.company.bookmanagement.repository.BookRepository;
import com.company.bookmanagement.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Book Service Implementation
 *
 * Contains the business logic for book operations.
 * Orchestrates between controller and repository layers.
 *
 * RESPONSIBILITIES:
 * 1. Business logic implementation
 * 2. Data transformation (DTO <-> Entity)
 * 3. Exception handling for business rules
 * 4. Logging business operations
 *
 * WORKFLOW for createBook():
 * 1. Receive CreateBookRequest from controller
 * 2. Map request to Book entity
 * 3. Save to repository
 * 4. Map entity to BookResponse
 * 5. Return response
 *
 * DEPENDENCY INJECTION:
 * - @RequiredArgsConstructor generates constructor
 * - Spring injects BookRepository and BookMapper
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    /**
     * Creates a new book
     *
     * WORKFLOW:
     * 1. Log the operation start
     * 2. Map DTO to entity (id, timestamps handled by repo)
     * 3. Save to repository
     * 4. Map saved entity to response DTO
     * 5. Log success and return
     *
     * @param request CreateBookRequest with book data
     * @return BookResponse with created book data
     */
    @Override
    public BookResponse createBook(CreateBookRequest request) {
        log.info("Creating new book with title: '{}'", request.getTitle());

        // Map request DTO to entity
        Book book = bookMapper.toEntity(request);

        // Save to repository (ID and timestamps set here)
        Book savedBook = bookRepository.save(book);

        log.info("Book created successfully with ID: {}", savedBook.getId());

        // Map entity to response DTO and return
        return bookMapper.toResponse(savedBook);
    }

    /**
     * Gets all books
     *
     * @return List of BookResponse (may be empty)
     */
    @Override
    public List<BookResponse> getAllBooks() {
        log.info("Fetching all books");

        List<Book> books = bookRepository.findAll();

        log.info("Found {} books", books.size());

        return bookMapper.toResponseList(books);
    }

    /**
     * Gets a book by ID
     *
     * @param id Book ID to find
     * @return BookResponse with book data
     * @throws BookNotFoundException if book doesn't exist
     */
    @Override
    public BookResponse getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with ID: {}", id);
                    return new BookNotFoundException(id);
                });

        log.debug("Found book: {}", book.getTitle());

        return bookMapper.toResponse(book);
    }

    /**
     * Updates an existing book
     *
     * WORKFLOW:
     * 1. Find existing book or throw 404
     * 2. Map non-null request fields to entity
     * 3. Save updated entity
     * 4. Return response
     *
     * @param id      Book ID to update
     * @param request UpdateBookRequest with new values
     * @return BookResponse with updated data
     * @throws BookNotFoundException if book doesn't exist
     */
    @Override
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        log.info("Updating book with ID: {}", id);

        // Find existing book
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update - book not found with ID: {}", id);
                    return new BookNotFoundException(id);
                });

        // Update only non-null fields (MapStruct handles this)
        bookMapper.updateEntityFromRequest(request, existingBook);

        // Save updated book
        Book updatedBook = bookRepository.save(existingBook);

        log.info("Book updated successfully: {}", id);

        return bookMapper.toResponse(updatedBook);
    }

    /**
     * Deletes a book
     *
     * IDEMPOTENCY NOTE:
     * - First delete: Returns 200 with deleted book
     * - Second delete: Returns 404 (book not found)
     *
     * @param id Book ID to delete
     * @return BookResponse with deleted book data
     * @throws BookNotFoundException if book doesn't exist
     */
    @Override
    public BookResponse deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);

        // Find book first (to return it in response)
        Book bookToDelete = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot delete - book not found with ID: {}", id);
                    return new BookNotFoundException(id);
                });

        // Create response before deletion
        BookResponse response = bookMapper.toResponse(bookToDelete);

        // Delete from repository
        bookRepository.deleteById(id);

        log.info("Book deleted successfully: {}", id);

        return response;
    }
}