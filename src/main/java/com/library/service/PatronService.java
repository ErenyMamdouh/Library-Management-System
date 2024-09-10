package com.library.service;

import com.library.dto.PatronDto;
import com.library.entity.Patron;

import java.util.List;

public interface PatronService {
    Patron savepatron(PatronDto patronDto);

    PatronDto getPatronByid(Long id);

    List<PatronDto> getallpatrons();

    PatronDto editePatron(PatronDto patronDto, Long id);

    void deletePatronById(Long id);

}
