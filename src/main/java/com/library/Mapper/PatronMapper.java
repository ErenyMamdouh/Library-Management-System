package com.library.Mapper;

import com.library.DTO.PatronDto;
import com.library.Entity.Patron;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PatronMapper {
    PatronMapper INSTANCE = Mappers.getMapper(PatronMapper.class);


    //map entity to dto
    PatronDto mapToDto(Patron entity);


    //map dto to entity

   Patron mapToEntity(PatronDto patronDto);
}
