// src/main/java/com/company/bookmanagement/mapper/BookMapper.java
package com.company.bookmanagement.mapper;

import com.company.bookmanagement.model.dto.request.CreateBookRequest;
import com.company.bookmanagement.model.dto.request.UpdateBookRequest;
import com.company.bookmanagement.model.dto.response.BookResponse;
import com.company.bookmanagement.model.entity.Book;
import org.mapstruct.*;

import java.util.List;

/**
 * Book Mapper Interface
 *
 * Uses MapStruct to generate mapping code between entities and DTOs.
 * MapStruct generates implementation at compile time - no runtime reflection.
 *
 * BENEFITS:
 * 1. Type-safe mapping
 * 2. Compile-time code generation
 * 3. Better performance than reflection-based mappers
 * 4. Easier to maintain
 *
 * WORKFLOW:
 * 1. Define mapping methods in interface
 * 2. Maven compiles and MapStruct generates implementation
 * 3. Spring injects the generated implementation
 * 4. Use mapper in service layer
 *
 * GENERATED CODE LOCATION:
 * target/generated-sources/annotations/com/company/bookmanagement/mapper/BookMapperImpl.java
 */
@Mapper(
        componentModel = "spring",  // Makes it a Spring bean
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE  // Ignore null values in updates
)
public interface BookMapper {

    /**
     * Converts Book entity to BookResponse DTO
     *
     * @param book The entity to convert
     * @return The response DTO
     */
    BookResponse toResponse(Book book);

    /**
     * Converts list of Book entities to list of BookResponse DTOs
     *
     * @param books List of entities
     * @return List of response DTOs
     */
    List<BookResponse> toResponseList(List<Book> books);

    /**
     * Converts CreateBookRequest DTO to Book entity
     *
     * @param request The create request
     * @return New Book entity (id will be null)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(CreateBookRequest request);

    /**
     * Updates existing Book entity from UpdateBookRequest
     * Only non-null fields in the request will update the entity
     *
     * @param request The update request
     * @param book    The existing book entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(UpdateBookRequest request, @MappingTarget Book book);
}