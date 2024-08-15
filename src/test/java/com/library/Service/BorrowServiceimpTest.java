package com.library.Service;


import com.library.DTO.BoorowDto;
import com.library.Entity.Book;
import com.library.Entity.BorrowingRecord;
import com.library.Entity.Patron;
import com.library.Exception.BookException;
import com.library.Exception.PatronException;
import com.library.Mapper.BorrowMapper;
import com.library.Repository.BookRepo;
import com.library.Repository.BorrowingRecordRepo;
import com.library.Repository.PatronRepo;
import com.library.Service.imp.BorrowServiceimp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BorrowServiceimpTest {

    @Mock
    private BorrowingRecordRepo borrowingRecordRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private BorrowMapper borrowMapper;
    @Mock
    private PatronRepo patronRepo;

    @InjectMocks
    private BorrowServiceimp borrowServiceimp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {

        Long bookId = 1L;
        Long patronId = 1L;
        Book book = new Book();
        book.setBookId(bookId);
        Patron patron = new Patron();
        patron.setPId(patronId);

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(new Date())
                .dueDate(calculateDueDate())
                .build();

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepo.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepo.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        // Act
        BoorowDto boorowDto = borrowServiceimp.borrowBook(bookId, patronId);


        assertNotNull(boorowDto, "Borrow DTO should not be null");
        assertEquals(bookId, boorowDto.getBookId(), "The book ID should match");
        assertEquals(patronId, boorowDto.getPId(), "The patron ID should match");
    }

    @Test
    void testBorrowBookThrowsExceptionWhenBookNotFound() {

        Long bookId = 1L;
        Long patronId = 1L;

        when(bookRepo.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookException.class, () -> borrowServiceimp.borrowBook(bookId, patronId), "Exception should be thrown when book not found");
    }

    @Test
    void testBorrowBookThrowsExceptionWhenPatronNotFound() {

        Long bookId = 1L;
        Long patronId = 1L;
        Book book = new Book();
        book.setBookId(bookId);

        when(bookRepo.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepo.findById(patronId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PatronException.class, () -> borrowServiceimp.borrowBook(bookId, patronId), "Exception should be thrown when patron not found");
    }

    @Test
    void testReturnBook() {

        Long bookId = 1L;
        Long patronId = 1L;

        Book book = new Book();
        book.setBookId(bookId);

        Patron patron = new Patron();
        patron.setPId(patronId);

        Date returnDate = new Date(); // Capture the returnDate for comparison

        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(new Date())
                .dueDate(calculateDueDate())
                .returnDate(returnDate) // Use captured returnDate
                .build();

        BoorowDto expectedDto = new BoorowDto();
        expectedDto.setBookId(bookId);
        expectedDto.setPId(patronId);
        expectedDto.setRecordId(borrowingRecord.getRecordId());
        expectedDto.setBorrowDate(borrowingRecord.getBorrowDate());
        expectedDto.setDueDate(borrowingRecord.getDueDate());
        expectedDto.setReturnDate(returnDate); // Use captured returnDate

        // Mock the repositories
        when(borrowingRecordRepo.findByBook_BookIdAndPatron_PId(bookId, patronId))
                .thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepo.save(any(BorrowingRecord.class)))
                .thenReturn(borrowingRecord);

        // Mock the BorrowMapper
        when(borrowMapper.toDto(borrowingRecord)).thenReturn(expectedDto);

        // Act
        BoorowDto actualDto = borrowServiceimp.returnBook(bookId, patronId);


        assertNotNull(actualDto, "Borrow DTO should not be null");
        assertEquals(expectedDto.getBookId(), actualDto.getBookId(), "The book ID should match");
        assertEquals(expectedDto.getPId(), actualDto.getPId(), "The patron ID should match");

    }

    @Test
    void testReturnBookThrowsExceptionWhenRecordNotFound() {

        Long bookId = 1L;
        Long patronId = 1L;

        when(borrowingRecordRepo.findByBook_BookIdAndPatron_PId(bookId, patronId)).thenReturn(Optional.empty());


        assertThrows(BookException.class, () -> borrowServiceimp.returnBook(bookId, patronId), "Exception should be thrown when borrowing record not found");
    }

    private Date calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14);
        return calendar.getTime();
    }
}
