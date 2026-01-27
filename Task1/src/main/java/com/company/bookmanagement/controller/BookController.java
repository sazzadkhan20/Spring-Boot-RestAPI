package com.company.bookmanagement.controller;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.ApiResponse;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.model.dto.response.ErrorResponse;
import com.company.bookmanagement.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

    private final BookService bookService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book with the provided details. Title and author are required."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error - invalid input",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timestamp": "2024-01-15T10:30:00",
                                        "status": 400,
                                        "error": "Bad Request",
                                        "errorCode": "VAL-001",
                                        "message": "Validation failed",
                                        "fieldErrors": {
                                            "title": "Title is required",
                                            "author": "Author is required"
                                        }
                                    }
                                    """
                            )
                    )
            )
    })
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody CreateBookRequest request) {

        log.info("POST /api/books - Creating book: {}", request.getTitle());

        BookResponse createdBook = bookService.createBook(request);

        log.info("Book created with ID: {}", createdBook.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all books",
            description = "Returns a list of all books in the system. Returns empty array if no books exist."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved all books",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            )
    })
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info("GET /api/books - Fetching all books");

        List<BookResponse> books = bookService.getAllBooks();

        log.info("Returning {} books", books.size());

        return ResponseEntity.ok(books);
    }
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Get book by ID",
            description = "Returns a single book by its ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Book found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                    {
                                        "timestamp": "2024-01-15T10:30:00",
                                        "status": 404,
                                        "error": "Not Found",
                                        "errorCode": "BOOK-001",
                                        "message": "Book not found",
                                        "details": "Book with ID 123 not found"
                                    }
                                    """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID format",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "ID of the book to retrieve", required = true, example = "1")
            @PathVariable Long id) {

        log.info("GET /api/books/{} - Fetching book", id);

        BookResponse book = bookService.getBookById(id);

        log.info("Found book: {}", book.getTitle());

        return ResponseEntity.ok(book);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update a book",
            description = "Updates an existing book. Only provided fields will be updated."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "ID of the book to update", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookRequest request) {

        log.info("PUT /api/books/{} - Updating book", id);

        BookResponse updatedBook = bookService.updateBook(id, request);

        log.info("Book updated: {}", updatedBook.getId());

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by ID. Returns the deleted book data."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Book deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<BookResponse> deleteBook(
            @Parameter(description = "ID of the book to delete", required = true, example = "1")
            @PathVariable Long id) {

        log.info("DELETE /api/books/{} - Deleting book", id);

        BookResponse deletedBook = bookService.deleteBook(id);

        log.info("Book deleted: {}", id);

        return ResponseEntity.ok(deletedBook);
    }
}