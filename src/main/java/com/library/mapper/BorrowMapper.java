package com.library.mapper;

import com.library.dto.BoorowDto;
import com.library.entity.BorrowingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BorrowMapper {
    BorrowMapper INSTANCE = Mappers.getMapper(BorrowMapper.class);

    // Map entity to DTO
    @Mapping(source = "book.bookId", target = "bookId")
    @Mapping(source = "patron.PId", target = "PId")
    BoorowDto toDto(BorrowingRecord borrowingRecord);

}
