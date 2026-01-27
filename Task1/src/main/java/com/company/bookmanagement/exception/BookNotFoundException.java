package com.company.bookmanagement.exception;

public class BookNotFoundException extends BaseException {

    public BookNotFoundException(Long id) {
        super(ErrorCode.BOOK_NOT_FOUND, "Book with ID " + id + " not found");
    }
    public BookNotFoundException(String message) {
        super(ErrorCode.BOOK_NOT_FOUND, message);
    }
}