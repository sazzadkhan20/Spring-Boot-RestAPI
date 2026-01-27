// src/main/java/com/company/bookmanagement/repository/impl/InMemoryBookRepository.java
package com.company.bookmanagement.repository.impl;

import com.company.bookmanagement.model.entity.Book;
import com.company.bookmanagement.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-Memory Book Repository Implementation
 *
 * Stores books in memory using a ConcurrentHashMap.
 * Thread-safe implementation suitable for concurrent access.
 *
 * DATA STRUCTURE:
 * - ConcurrentHashMap: Thread-safe storage for books
 * - AtomicLong: Thread-safe ID generation
 *
 * THREAD SAFETY:
 * - ConcurrentHashMap handles concurrent read/write
 * - AtomicLong ensures unique ID generation
 *
 * NOTE: Data is lost when application restarts
 * For persistence, implement JpaBookRepository
 */
@Repository
@Slf4j
public class InMemoryBookRepository implements BookRepository {

    /**
     * Thread-safe storage for books
     * Key: Book ID, Value: Book entity
     */
    private final Map<Long, Book> bookStorage = new ConcurrentHashMap<>();

    /**
     * Thread-safe ID generator
     * Starts at 0, each new book gets incremented value
     */
    private final AtomicLong idGenerator = new AtomicLong(0);

    /**
     * Saves a book to storage
     *
     * WORKFLOW:
     * 1. If book has no ID (new), generate one
     * 2. Set createdAt timestamp for new books
     * 3. Set updatedAt timestamp for all saves
     * 4. Store in ConcurrentHashMap
     * 5. Return saved book
     *
     * @param book The book to save
     * @return The saved book with ID and timestamps
     */
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            // New book - generate ID and set creation time
            book.setId(idGenerator.incrementAndGet());
            book.setCreatedAt(LocalDateTime.now());
            log.info("Creating new book with ID: {}", book.getId());
        }

        // Always update the updatedAt timestamp
        book.setUpdatedAt(LocalDateTime.now());

        // Store in map
        bookStorage.put(book.getId(), book);

        log.debug("Book saved: {}", book);
        return book;
    }

    /**
     * Finds a book by ID
     *
     * @param id The book ID to find
     * @return Optional with book if found, empty otherwise
     */
    @Override
    public Optional<Book> findById(Long id) {
        log.debug("Finding book with ID: {}", id);
        return Optional.ofNullable(bookStorage.get(id));
    }

    /**
     * Returns all books
     *
     * @return List of all books (copy to prevent external modification)
     */
    @Override
    public List<Book> findAll() {
        log.debug("Finding all books, count: {}", bookStorage.size());
        return new ArrayList<>(bookStorage.values());
    }

    /**
     * Deletes a book by ID
     *
     * @param id The book ID to delete
     * @return true if book was deleted, false if not found
     */
    @Override
    public boolean deleteById(Long id) {
        log.info("Attempting to delete book with ID: {}", id);
        Book removed = bookStorage.remove(id);

        if (removed != null) {
            log.info("Book deleted: {}", id);
            return true;
        }

        log.warn("Book not found for deletion: {}", id);
        return false;
    }

    /**
     * Checks if a book exists
     *
     * @param id The book ID to check
     * @return true if exists, false otherwise
     */
    @Override
    public boolean existsById(Long id) {
        return bookStorage.containsKey(id);
    }

    /**
     * Returns total count of books
     *
     * @return Number of books in storage
     */
    @Override
    public long count() {
        return bookStorage.size();
    }

    /**
     * Clears all books from storage
     * Useful for testing
     */
    @Override
    public void deleteAll() {
        log.warn("Deleting all books from storage");
        bookStorage.clear();
        idGenerator.set(0);  // Reset ID counter
    }
}