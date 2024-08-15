package com.library.Mapper;

import com.library.DTO.BookDto;
import com.library.Entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    // map entity to dto
    BookDto mapToDto(Book entity);

    // map dto to entity
    Book mapToEntity(BookDto bookDto);
}
