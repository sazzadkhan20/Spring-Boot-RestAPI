// src/main/java/com/company/bookmanagement/repository/BookRepository.java
package com.company.bookmanagement.repository;

import com.company.bookmanagement.model.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Book Repository Interface
 *
 * Defines the contract for book data access operations.
 * This abstraction allows easy switching between storage implementations.
 *
 * CURRENT IMPLEMENTATION: InMemoryBookRepository
 * FUTURE IMPLEMENTATIONS: JpaBookRepository, MongoBookRepository, etc.
 *
 * DESIGN PATTERN: Repository Pattern
 * - Separates data access logic from business logic
 * - Provides collection-like interface for domain objects
 */
public interface BookRepository {

    /**
     * Saves a book to the repository
     *
     * @param book The book to save
     * @return The saved book with generated ID
     */
    Book save(Book book);

    /**
     * Finds a book by its ID
     *
     * @param id The book ID
     * @return Optional containing the book if found
     */
    Optional<Book> findById(Long id);

    /**
     * Returns all books in the repository
     *
     * @return List of all books
     */
    List<Book> findAll();

    /**
     * Deletes a book by its ID
     *
     * @param id The book ID to delete
     * @return true if deleted, false if not found
     */
    boolean deleteById(Long id);

    /**
     * Checks if a book exists with the given ID
     *
     * @param id The book ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Returns the count of books in the repository
     *
     * @return Number of books
     */
    long count();

    /**
     * Deletes all books from the repository
     */
    void deleteAll();
}