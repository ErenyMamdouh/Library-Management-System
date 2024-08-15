package com.library.Controller;

import com.library.DTO.BookDto;
import com.library.Entity.Book;
import com.library.Service.imp.BookServiceimp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    BookServiceimp bookServiceimp;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody @Valid BookDto bookDto) {
        return new ResponseEntity<>(bookServiceimp.saveBook(bookDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable Long id) {
        BookDto bookDto = bookServiceimp.getBookById(id);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookServiceimp.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@RequestBody @Valid BookDto bookDto, @PathVariable Long id) {
        return bookServiceimp.editBook(bookDto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        bookServiceimp.deleteBookById(id);
        return ResponseEntity.ok("Book is deleted successfully!");
    }
}
