package com.company.bookmanagement.integration;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Book API Integration Tests")
class BookApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("Complete CRUD workflow")
    void completeCrudWorkflow() throws Exception {
        // 1. CREATE - Create a new book
        CreateBookRequest createRequest = CreateBookRequest.builder()
                .title("Test Book")
                .author("Test Author")
                .year(2020)
                .genre("Test Genre")
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test Book")))
                .andReturn();

        BookResponse createdBook = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                BookResponse.class
        );
        Long bookId = createdBook.getId();

        // 2. READ - Get the created book
        mockMvc.perform(get("/api/books/{id}", bookId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookId.intValue())))
                .andExpect(jsonPath("$.title", is("Test Book")));

        // 3. READ ALL - Get all books
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // 4. UPDATE - Update the book
        UpdateBookRequest updateRequest = UpdateBookRequest.builder()
                .title("Updated Book Title")
                .year(2021)
                .build();

        mockMvc.perform(put("/api/books/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Book Title")))
                .andExpect(jsonPath("$.year", is(2021)))
                .andExpect(jsonPath("$.author", is("Test Author"))); // Unchanged

        // 5. DELETE - Delete the book
        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bookId.intValue())));

        // 6. VERIFY DELETE - Try to get deleted book (should be 404)
        mockMvc.perform(get("/api/books/{id}", bookId))
                .andDo(print())
                .andExpect(status().isNotFound());

        // 7. IDEMPOTENCY - Delete again (should be 404)
        mockMvc.perform(delete("/api/books/{id}", bookId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    @DisplayName("Validation errors return 400")
    void validationErrorsReturn400() throws Exception {
        // Missing required fields
        CreateBookRequest invalidRequest = CreateBookRequest.builder()
                .build();

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VAL-001")))
                .andExpect(jsonPath("$.fieldErrors.title", notNullValue()))
                .andExpect(jsonPath("$.fieldErrors.author", notNullValue()));
    }

    @Test
    @Order(3)
    @DisplayName("Non-existing book returns 404")
    void nonExistingBookReturns404() throws Exception {
        mockMvc.perform(get("/api/books/99999"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("BOOK-001")))
                .andExpect(jsonPath("$.message", is("Book not found")));
    }

    @Test
    @Order(4)
    @DisplayName("Invalid path parameter returns 400")
    void invalidPathParameterReturns400() throws Exception {
        mockMvc.perform(get("/api/books/invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is("VAL-007")));
    }
}