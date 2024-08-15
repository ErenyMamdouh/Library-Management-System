package com.library.Service.imp;

import com.library.DTO.BookDto;
import com.library.Entity.Book;
import com.library.Exception.BookException;
import com.library.Mapper.BookMapper;
import com.library.Repository.BookRepo;
import com.library.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceimp implements BookService {
    @Autowired
    private BookRepo bookRepo;

    @Override
    public Book saveBook(BookDto bookDto){
        Book book = BookMapper.INSTANCE.mapToEntity(bookDto);
        return bookRepo.save(book);
    }

    @Override
    public BookDto getBookById(Long id){
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
        return BookMapper.INSTANCE.mapToDto(book);
    }

    @Override
    public List<BookDto> getAllBooks(){
        List<Book> books = bookRepo.findAll();
        return books.stream()
                .map(BookMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Book editBook(BookDto bookDto, Long id){
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
        Book updatedBook = BookMapper.INSTANCE.mapToEntity(bookDto);
        updatedBook.setBookId(book.getBookId());
        return bookRepo.save(updatedBook);
    }


    @Override
    public void deleteBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookException("Book doesn't exist with id: " + id));

        bookRepo.deleteById(id);
    }
}
