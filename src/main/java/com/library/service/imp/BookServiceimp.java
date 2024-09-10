package com.library.service.imp;

import com.library.dto.BookDto;
import com.library.entity.Book;
import com.library.exception.BookException;
import com.library.mapper.BookMapper;
import com.library.dao.BookRepo;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceimp implements BookService {

    @Autowired
    private BookRepo bookRepo;


    @Override
    @CacheEvict(value = "books", allEntries = true)
    public Book saveBook(BookDto bookDto){
        Book book = BookMapper.INSTANCE.mapToEntity(bookDto);
        return bookRepo.save(book);
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public BookDto getBookById(Long id){
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
        return BookMapper.INSTANCE.mapToDto(book);
    }

    @Override
    @Cacheable(value = "books", key = "#root.methodName")
    public List<BookDto> getAllBooks(){
        List<Book> books = bookRepo.findAll();
        return books.stream()
                .map(BookMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "books", key = "#bookDto.id",allEntries = true)
    public Book editBook(BookDto bookDto, Long id){
        Book book = bookRepo.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
        Book updatedBook = BookMapper.INSTANCE.mapToEntity(bookDto);
        updatedBook.setBookId(book.getBookId());
        return bookRepo.save(updatedBook);
    }


    @Override
    @CacheEvict(value = "books", key = "#id",allEntries = true)
    public void deleteBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new BookException("Book doesn't exist with id: " + id));

        bookRepo.deleteById(id);
    }
}
