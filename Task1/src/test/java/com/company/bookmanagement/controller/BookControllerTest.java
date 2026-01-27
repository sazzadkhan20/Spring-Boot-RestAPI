// src/test/java/com/company/bookmanagement/controller/BookControllerTest.java
package com.company.bookmanagement.controller;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Book Controller Unit Tests
 *
 * Tests the controller layer in isolation using MockMvc.
 * Service layer is mocked to focus on HTTP request/response handling.
 *
 * TEST STRUCTURE:
 * - Nested classes group related tests
 * - Each endpoint has its own nested class
 * - Tests cover success and error scenarios
 */
@WebMvcTest(BookController.class)
@DisplayName("Book Controller Tests")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    private BookResponse sampleBookResponse;
    private CreateBookRequest validCreateRequest;

    @BeforeEach
    void setUp() {
        // Setup sample data used across tests
        sampleBookResponse = BookResponse.builder()
                .id(1L)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        validCreateRequest = CreateBookRequest.builder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .build();
    }

    // ==================== POST /api/books Tests ====================

    @Nested
    @DisplayName("POST /api/books")
    class CreateBookTests {

        @Test
        @DisplayName("Should create book successfully with valid request")
        void shouldCreateBookSuccessfully() throws Exception {
            // Given
            given(bookService.createBook(any(CreateBookRequest.class)))
                    .willReturn(sampleBookResponse);

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validCreateRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.title", is("The Great Gatsby")))
                    .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald")))
                    .andExpect(jsonPath("$.year", is(1925)))
                    .andExpect(jsonPath("$.genre", is("Fiction")));
        }

        @Test
        @DisplayName("Should return 400 when title is missing")
        void shouldReturn400WhenTitleMissing() throws Exception {
            // Given
            CreateBookRequest invalidRequest = CreateBookRequest.builder()
                    .author("Author Name")
                    .build();

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is("VAL-001")))
                    .andExpect(jsonPath("$.fieldErrors.title", notNullValue()));
        }

        @Test
        @DisplayName("Should return 400 when author is missing")
        void shouldReturn400WhenAuthorMissing() throws Exception {
            // Given
            CreateBookRequest invalidRequest = CreateBookRequest.builder()
                    .title("Book Title")
                    .build();

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is("VAL-001")))
                    .andExpect(jsonPath("$.fieldErrors.author", notNullValue()));
        }

        @Test
        @DisplayName("Should return 400 when title exceeds max length")
        void shouldReturn400WhenTitleTooLong() throws Exception {
            // Given
            String longTitle = "A".repeat(201);  // 201 characters
            CreateBookRequest invalidRequest = CreateBookRequest.builder()
                    .title(longTitle)
                    .author("Author Name")
                    .build();

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.fieldErrors.title", notNullValue()));
        }

        @Test
        @DisplayName("Should return 400 when year is before 1800")
        void shouldReturn400WhenYearTooOld() throws Exception {
            // Given
            CreateBookRequest invalidRequest = CreateBookRequest.builder()
                    .title("Ancient Book")
                    .author("Author Name")
                    .year(1799)
                    .build();

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.fieldErrors.year", notNullValue()));
        }

        @Test
        @DisplayName("Should return 400 when year is in future")
        void shouldReturn400WhenYearInFuture() throws Exception {
            // Given
            CreateBookRequest invalidRequest = CreateBookRequest.builder()
                    .title("Future Book")
                    .author("Author Name")
                    .year(2050)
                    .build();

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.fieldErrors.year", notNullValue()));
        }

        @Test
        @DisplayName("Should create book with optional fields null")
        void shouldCreateBookWithOptionalFieldsNull() throws Exception {
            // Given
            CreateBookRequest minimalRequest = CreateBookRequest.builder()
                    .title("Minimal Book")
                    .author("Author Name")
                    .build();

            BookResponse minimalResponse = BookResponse.builder()
                    .id(1L)
                    .title("Minimal Book")
                    .author("Author Name")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            given(bookService.createBook(any(CreateBookRequest.class)))
                    .willReturn(minimalResponse);

            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(minimalRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title", is("Minimal Book")))
                    .andExpect(jsonPath("$.year").doesNotExist())
                    .andExpect(jsonPath("$.genre").doesNotExist());
        }

        @Test
        @DisplayName("Should return 400 when request body is empty")
        void shouldReturn400WhenRequestBodyEmpty() throws Exception {
            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when request body is invalid JSON")
        void shouldReturn400WhenInvalidJson() throws Exception {
            // When
            ResultActions result = mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ invalid json }"));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is("VAL-006")));
        }
    }

    // ==================== GET /api/books Tests ====================

    @Nested
    @DisplayName("GET /api/books")
    class GetAllBooksTests {

        @Test
        @DisplayName("Should return all books")
        void shouldReturnAllBooks() throws Exception {
            // Given
            List<BookResponse> books = Arrays.asList(
                    sampleBookResponse,
                    BookResponse.builder()
                            .id(2L)
                            .title("1984")
                            .author("George Orwell")
                            .year(1949)
                            .genre("Dystopian")
                            .build()
            );
            given(bookService.getAllBooks()).willReturn(books);

            // When
            ResultActions result = mockMvc.perform(get("/api/books")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].title", is("The Great Gatsby")))
                    .andExpect(jsonPath("$[1].id", is(2)))
                    .andExpect(jsonPath("$[1].title", is("1984")));
        }

        @Test
        @DisplayName("Should return empty array when no books exist")
        void shouldReturnEmptyArrayWhenNoBooksExist() throws Exception {
            // Given
            given(bookService.getAllBooks()).willReturn(Collections.emptyList());

            // When
            ResultActions result = mockMvc.perform(get("/api/books")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    // ==================== GET /api/books/{id} Tests ====================

    @Nested
    @DisplayName("GET /api/books/{id}")
    class GetBookByIdTests {

        @Test
        @DisplayName("Should return book when found")
        void shouldReturnBookWhenFound() throws Exception {
            // Given
            given(bookService.getBookById(1L)).willReturn(sampleBookResponse);

            // When
            ResultActions result = mockMvc.perform(get("/api/books/1")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.title", is("The Great Gatsby")));
        }

        @Test
        @DisplayName("Should return 404 when book not found")
        void shouldReturn404WhenBookNotFound() throws Exception {
            // Given
            given(bookService.getBookById(999L))
                    .willThrow(new com.company.bookmanagement.exception.BookNotFoundException(999L));

            // When
            ResultActions result = mockMvc.perform(get("/api/books/999")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode", is("BOOK-001")))
                    .andExpect(jsonPath("$.message", is("Book not found")));
        }

        @Test
        @DisplayName("Should return 400 when ID is not a number")
        void shouldReturn400WhenIdNotNumber() throws Exception {
            // When
            ResultActions result = mockMvc.perform(get("/api/books/abc")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode", is("VAL-007")));
        }
    }

    // ==================== PUT /api/books/{id} Tests ====================

    @Nested
    @DisplayName("PUT /api/books/{id}")
    class UpdateBookTests {

        @Test
        @DisplayName("Should update book successfully")
        void shouldUpdateBookSuccessfully() throws Exception {
            // Given
            UpdateBookRequest updateRequest = UpdateBookRequest.builder()
                    .title("The Great Gatsby - Updated")
                    .build();

            BookResponse updatedResponse = BookResponse.builder()
                    .id(1L)
                    .title("The Great Gatsby - Updated")
                    .author("F. Scott Fitzgerald")
                    .year(1925)
                    .genre("Fiction")
                    .createdAt(sampleBookResponse.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            given(bookService.updateBook(eq(1L), any(UpdateBookRequest.class)))
                    .willReturn(updatedResponse);

            // When
            ResultActions result = mockMvc.perform(put("/api/books/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.title", is("The Great Gatsby - Updated")));
        }

        @Test
        @DisplayName("Should return 404 when updating non-existent book")
        void shouldReturn404WhenUpdatingNonExistentBook() throws Exception {
            // Given
            UpdateBookRequest updateRequest = UpdateBookRequest.builder()
                    .title("Updated Title")
                    .build();

            given(bookService.updateBook(eq(999L), any(UpdateBookRequest.class)))
                    .willThrow(new com.company.bookmanagement.exception.BookNotFoundException(999L));

            // When
            ResultActions result = mockMvc.perform(put("/api/books/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode", is("BOOK-001")));
        }

        @Test
        @DisplayName("Should return 400 when update request has invalid data")
        void shouldReturn400WhenUpdateRequestInvalid() throws Exception {
            // Given
            UpdateBookRequest invalidRequest = UpdateBookRequest.builder()
                    .title("")  // Empty title is invalid
                    .build();

            // When
            ResultActions result = mockMvc.perform(put("/api/books/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(invalidRequest)));

            // Then
            result.andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    // ==================== DELETE /api/books/{id} Tests ====================

    @Nested
    @DisplayName("DELETE /api/books/{id}")
    class DeleteBookTests {

        @Test
        @DisplayName("Should delete book successfully")
        void shouldDeleteBookSuccessfully() throws Exception {
            // Given
            given(bookService.deleteBook(1L)).willReturn(sampleBookResponse);

            // When
            ResultActions result = mockMvc.perform(delete("/api/books/1")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.title", is("The Great Gatsby")));
        }

        @Test
        @DisplayName("Should return 404 when deleting non-existent book")
        void shouldReturn404WhenDeletingNonExistentBook() throws Exception {
            // Given
            given(bookService.deleteBook(999L))
                    .willThrow(new com.company.bookmanagement.exception.BookNotFoundException(999L));

            // When
            ResultActions result = mockMvc.perform(delete("/api/books/999")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode", is("BOOK-001")));
        }

        @Test
        @DisplayName("Should return 404 when deleting same book twice (idempotency)")
        void shouldReturn404WhenDeletingSameBookTwice() throws Exception {
            // Given - First delete succeeds
            given(bookService.deleteBook(1L))
                    .willReturn(sampleBookResponse)
                    .willThrow(new com.company.bookmanagement.exception.BookNotFoundException(1L));

            // When - First delete
            mockMvc.perform(delete("/api/books/1"))
                    .andExpect(status().isOk());

            // When - Second delete
            ResultActions result = mockMvc.perform(delete("/api/books/1")
                    .accept(MediaType.APPLICATION_JSON));

            // Then
            result.andDo(print())
                    .andExpect(status().isNotFound());
        }
    }
}