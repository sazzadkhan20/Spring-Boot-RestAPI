package com.company.bookmanagement.util;

public final class Constants {
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final int MIN_YEAR = 1800;
    public static final int MAX_TITLE_LENGTH = 200;
    public static final int MIN_TITLE_LENGTH = 1;
    public static final int MAX_AUTHOR_LENGTH = 200;
    public static final int MIN_AUTHOR_LENGTH = 1;
    public static final int MAX_GENRE_LENGTH = 100;
    public static final String API_BASE_PATH = "/api";
    public static final String BOOKS_PATH = "/books";
    public static final String BOOK_CREATED_SUCCESS = "Book created successfully";
    public static final String BOOK_UPDATED_SUCCESS = "Book updated successfully";
    public static final String BOOK_DELETED_SUCCESS = "Book deleted successfully";
    public static final String BOOKS_FETCHED_SUCCESS = "Books retrieved successfully";
}