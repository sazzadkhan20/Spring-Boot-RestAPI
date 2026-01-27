package com.company.bookmanagement.service;

import com.company.bookmanagement.exception.BookNotFoundException;
import com.company.bookmanagement.mapper.BookMapper;
import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.model.entity.Book;
import com.company.bookmanagement.repository.BookRepository;
import com.company.bookmanagement.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book Service Tests")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;
    private BookResponse sampleBookResponse;
    private CreateBookRequest createRequest;

    @BeforeEach
    void setUp() {
        sampleBook = Book.builder()
                .id(1L)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        sampleBookResponse = BookResponse.builder()
                .id(1L)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .createdAt(sampleBook.getCreatedAt())
                .updatedAt(sampleBook.getUpdatedAt())
                .build();

        createRequest = CreateBookRequest.builder()
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .year(1925)
                .genre("Fiction")
                .build();
    }

    @Nested
    @DisplayName("createBook()")
    class CreateBookTests {

        @Test
        @DisplayName("Should create book successfully")
        void shouldCreateBookSuccessfully() {
            // Given
            given(bookMapper.toEntity(createRequest)).willReturn(sampleBook);
            given(bookRepository.save(any(Book.class))).willReturn(sampleBook);
            given(bookMapper.toResponse(sampleBook)).willReturn(sampleBookResponse);

            // When
            BookResponse result = bookService.createBook(createRequest);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getTitle()).isEqualTo("The Great Gatsby");

            verify(bookMapper).toEntity(createRequest);
            verify(bookRepository).save(any(Book.class));
            verify(bookMapper).toResponse(sampleBook);
        }
    }

    @Nested
    @DisplayName("getAllBooks()")
    class GetAllBooksTests {

        @Test
        @DisplayName("Should return all books")
        void shouldReturnAllBooks() {
            // Given
            List<Book> books = Arrays.asList(sampleBook);
            List<BookResponse> responses = Arrays.asList(sampleBookResponse);

            given(bookRepository.findAll()).willReturn(books);
            given(bookMapper.toResponseList(books)).willReturn(responses);

            // When
            List<BookResponse> result = bookService.getAllBooks();

            // Then
            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTitle()).isEqualTo("The Great Gatsby");
        }

        @Test
        @DisplayName("Should return empty list when no books exist")
        void shouldReturnEmptyListWhenNoBooksExist() {
            // Given
            given(bookRepository.findAll()).willReturn(Collections.emptyList());
            given(bookMapper.toResponseList(Collections.emptyList())).willReturn(Collections.emptyList());

            // When
            List<BookResponse> result = bookService.getAllBooks();

            // Then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("getBookById()")
    class GetBookByIdTests {

        @Test
        @DisplayName("Should return book when found")
        void shouldReturnBookWhenFound() {
            // Given
            given(bookRepository.findById(1L)).willReturn(Optional.of(sampleBook));
            given(bookMapper.toResponse(sampleBook)).willReturn(sampleBookResponse);

            // When
            BookResponse result = bookService.getBookById(1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("Should throw BookNotFoundException when not found")
        void shouldThrowExceptionWhenNotFound() {
            // Given
            given(bookRepository.findById(999L)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> bookService.getBookById(999L))
                    .isInstanceOf(BookNotFoundException.class)
                    .hasMessageContaining("999");
        }
    }

    @Nested
    @DisplayName("updateBook()")
    class UpdateBookTests {

        @Test
        @DisplayName("Should update book successfully")
        void shouldUpdateBookSuccessfully() {
            // Given
            UpdateBookRequest updateRequest = UpdateBookRequest.builder()
                    .title("Updated Title")
                    .build();

            Book updatedBook = Book.builder()
                    .id(1L)
                    .title("Updated Title")
                    .author("F. Scott Fitzgerald")
                    .year(1925)
                    .genre("Fiction")
                    .createdAt(sampleBook.getCreatedAt())
                    .updatedAt(LocalDateTime.now())
                    .build();

            BookResponse updatedResponse = BookResponse.builder()
                    .id(1L)
                    .title("Updated Title")
                    .author("F. Scott Fitzgerald")
                    .year(1925)
                    .genre("Fiction")
                    .build();

            given(bookRepository.findById(1L)).willReturn(Optional.of(sampleBook));
            doNothing().when(bookMapper).updateEntityFromRequest(updateRequest, sampleBook);
            given(bookRepository.save(sampleBook)).willReturn(updatedBook);
            given(bookMapper.toResponse(updatedBook)).willReturn(updatedResponse);

            // When
            BookResponse result = bookService.updateBook(1L, updateRequest);

            // Then
            assertThat(result).isNotNull();
            verify(bookRepository).findById(1L);
            verify(bookMapper).updateEntityFromRequest(updateRequest, sampleBook);
            verify(bookRepository).save(sampleBook);
        }

        @Test
        @DisplayName("Should throw BookNotFoundException when book not found")
        void shouldThrowExceptionWhenBookNotFound() {
            // Given
            UpdateBookRequest updateRequest = UpdateBookRequest.builder()
                    .title("Updated Title")
                    .build();

            given(bookRepository.findById(999L)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> bookService.updateBook(999L, updateRequest))
                    .isInstanceOf(BookNotFoundException.class);

            verify(bookRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("deleteBook()")
    class DeleteBookTests {

        @Test
        @DisplayName("Should delete book successfully")
        void shouldDeleteBookSuccessfully() {
            // Given
            given(bookRepository.findById(1L)).willReturn(Optional.of(sampleBook));
            given(bookMapper.toResponse(sampleBook)).willReturn(sampleBookResponse);
            given(bookRepository.deleteById(1L)).willReturn(true);

            // When
            BookResponse result = bookService.deleteBook(1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            verify(bookRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Should throw BookNotFoundException when book not found")
        void shouldThrowExceptionWhenBookNotFound() {
            // Given
            given(bookRepository.findById(999L)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> bookService.deleteBook(999L))
                    .isInstanceOf(BookNotFoundException.class);

            verify(bookRepository, never()).deleteById(any());
        }
    }
}