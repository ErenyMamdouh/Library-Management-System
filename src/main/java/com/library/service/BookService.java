package com.library.service;

import com.library.dto.BookDto;
import com.library.entity.Book;

import java.util.List;

public interface BookService {
     Book saveBook(BookDto bookDto);

     BookDto getBookById(Long id);

    List<BookDto> getAllBooks();

    Book editBook(BookDto bookDto, Long id);

    void deleteBookById(Long id);
}
