package com.library.Service;
import com.library.DTO.BookDto;
import com.library.Entity.Book;
import com.library.Exception.BookException;
import com.library.Mapper.BookMapper;
import com.library.Repository.BookRepo;
import com.library.Service.imp.BookServiceimp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class BookServiceimpTest {
    @Mock
    private BookRepo bookRepo;

    @InjectMocks
    private BookServiceimp bookServiceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBook() {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuther("Test Author");
        bookDto.setPublicationDate(new Date());
        bookDto.setISBN("123456789");

        Book book = BookMapper.INSTANCE.mapToEntity(bookDto);

        when(bookRepo.save(any(Book.class))).thenReturn(book);

        // Act
        Book savedBook = bookServiceimp.saveBook(bookDto);

        // Assert
        assertNotNull(savedBook, "Saved book should not be null");
        assertEquals("Test Book", savedBook.getTitle(), "The title should match");
    }

    @Test
    void testGetBookById() {
        // Arrange
        Long id = 1L;
        Book book = new Book();
        book.setBookId(id);
        book.setTitle("Test Book");

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        // Act
        BookDto bookDto = bookServiceimp.getBookById(id);

        // Assert
        assertNotNull(bookDto, "Book DTO should not be null");
        assertEquals("Test Book", bookDto.getTitle(), "The title should match");
    }

    @Test
    void testGetBookByIdThrowsException() {
        // Arrange
        Long id = 1L;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookException.class, () -> bookServiceimp.getBookById(id), "Exception should be thrown when book not found");
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Test Book 1");
        Book book2 = new Book();
        book2.setTitle("Test Book 2");

        when(bookRepo.findAll()).thenReturn(List.of(book1, book2));

        // Act
        List<BookDto> books = bookServiceimp.getAllBooks();

        // Assert
        assertNotNull(books, "Books list should not be null");
        assertEquals(2, books.size(), "There should be two books in the list");
    }

    @Test
    void testEditBook() {
        // Arrange
        Long id = 1L;
        Book existingBook = new Book();
        existingBook.setBookId(id);
        existingBook.setTitle("Old Title");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("New Title");

        when(bookRepo.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepo.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Book updatedBook = bookServiceimp.editBook(bookDto, id);

        // Assert
        assertNotNull(updatedBook, "Updated book should not be null");
        assertEquals("New Title", updatedBook.getTitle(), "The title should be updated");
    }

    @Test
    void testDeleteBookById() {
        // Arrange
        Long id = 1L;
        Book book = new Book();
        book.setBookId(id);

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookRepo).deleteById(id);

        // Act
        bookServiceimp.deleteBookById(id);

        // Assert
        verify(bookRepo, times(1)).deleteById(id);
    }

    @Test
    void testDeleteBookByIdThrowsException() {
        // Arrange
        Long id = 1L;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookException.class, () -> bookServiceimp.deleteBookById(id), "Exception should be thrown when book not found");
    }

}
