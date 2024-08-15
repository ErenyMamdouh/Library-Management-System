package com.library.Service;

import com.library.DTO.PatronDto;
import com.library.Entity.Patron;

import java.util.List;

public interface PatronService {
    Patron savepatron(PatronDto patronDto);

    PatronDto getPatronByid(Long id);

    List<PatronDto> getallpatrons();

    PatronDto editePatron(PatronDto patronDto, Long id);

    void deletePatronById(Long id);

}
