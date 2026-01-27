package com.company.bookmanagement.repository;

import com.company.bookmanagement.model.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    boolean deleteById(Long id);
    boolean existsById(Long id);
    long count();
    void deleteAll();
}