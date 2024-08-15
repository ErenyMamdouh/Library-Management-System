package com.library.Service;

import com.library.DTO.BookDto;
import com.library.Entity.Book;

import java.util.List;

public interface BookService {
     Book saveBook(BookDto bookDto);

     BookDto getBookById(Long id);

    List<BookDto> getAllBooks();

    Book editBook(BookDto bookDto, Long id);

    void deleteBookById(Long id);
}
