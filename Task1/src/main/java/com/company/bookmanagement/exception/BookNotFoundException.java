// src/main/java/com/company/bookmanagement/exception/BookNotFoundException.java
package com.company.bookmanagement.exception;

/**
 * Book Not Found Exception
 *
 * Thrown when a book with the specified ID does not exist in the repository.
 * This is a specific exception for the 404 Not Found scenario.
 *
 * USAGE:
 * - In BookService.getBookById() when book doesn't exist
 * - In BookService.updateBook() when trying to update non-existent book
 * - In BookService.deleteBook() when trying to delete non-existent book
 *
 * EXAMPLE:
 * throw new BookNotFoundException(123L);
 * // Results in: {"error": "Book not found", "details": "Book with ID 123 not found"}
 */
public class BookNotFoundException extends BaseException {

    /**
     * Constructor with book ID
     *
     * @param id The ID of the book that was not found
     */
    public BookNotFoundException(Long id) {
        super(ErrorCode.BOOK_NOT_FOUND, "Book with ID " + id + " not found");
    }

    /**
     * Constructor with custom message
     *
     * @param message Custom error message
     */
    public BookNotFoundException(String message) {
        super(ErrorCode.BOOK_NOT_FOUND, message);
    }
}