package com.company.bookmanagement.repository.impl;

import com.company.bookmanagement.model.entity.Book;
import com.company.bookmanagement.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class InMemoryBookRepository implements BookRepository {
    private final Map<Long, Book> bookStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(idGenerator.incrementAndGet());
            book.setCreatedAt(LocalDateTime.now());
            log.info("Creating new book with ID: {}", book.getId());
        }
        book.setUpdatedAt(LocalDateTime.now());
        bookStorage.put(book.getId(), book);

        log.debug("Book saved: {}", book);
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        log.debug("Finding book with ID: {}", id);
        return Optional.ofNullable(bookStorage.get(id));
    }
    @Override
    public List<Book> findAll() {
        log.debug("Finding all books, count: {}", bookStorage.size());
        return new ArrayList<>(bookStorage.values());
    }
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
    @Override
    public boolean existsById(Long id) {
        return bookStorage.containsKey(id);
    }
    @Override
    public long count() {
        return bookStorage.size();
    }
    @Override
    public void deleteAll() {
        log.warn("Deleting all books from storage");
        bookStorage.clear();
        idGenerator.set(0);  // Reset ID counter
    }
}