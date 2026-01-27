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

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    @Override
    public BookResponse createBook(CreateBookRequest request) {
        log.info("Creating new book with title: '{}'", request.getTitle());

        Book book = bookMapper.toEntity(request);

        Book savedBook = bookRepository.save(book);

        log.info("Book created successfully with ID: {}", savedBook.getId());

        return bookMapper.toResponse(savedBook);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        log.info("Fetching all books");

        List<Book> books = bookRepository.findAll();

        log.info("Found {} books", books.size());

        return bookMapper.toResponseList(books);
    }
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

    @Override
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        log.info("Updating book with ID: {}", id);

        // Find existing book
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cannot update - book not found with ID: {}", id);
                    return new BookNotFoundException(id);
                });
        bookMapper.updateEntityFromRequest(request, existingBook);

        Book updatedBook = bookRepository.save(existingBook);

        log.info("Book updated successfully: {}", id);

        return bookMapper.toResponse(updatedBook);
    }

    @Override
    public BookResponse deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
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