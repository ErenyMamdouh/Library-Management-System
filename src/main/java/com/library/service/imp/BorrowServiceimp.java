package com.library.service.imp;

import com.library.dto.BoorowDto;
import com.library.entity.Book;
import com.library.entity.BorrowingRecord;
import com.library.entity.Patron;
import com.library.exception.BookException;
import com.library.exception.PatronException;
import com.library.mapper.BorrowMapper;
import com.library.dao.BookRepo;
import com.library.dao.BorrowingRecordRepo;
import com.library.dao.PatronRepo;
import com.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class BorrowServiceimp implements BorrowService {
    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    private final BorrowMapper borrowMapper = BorrowMapper.INSTANCE;

    @Transactional
    @Override
    @CacheEvict(value = {"patrons", "books"}, allEntries = true)
    public BoorowDto borrowBook(Long bookId, Long patronId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found with id: " + bookId));
        Patron patron = patronRepo.findById(patronId)
                .orElseThrow(() -> new PatronException("Patron not found with id: " + patronId));


        BorrowingRecord borrowingRecord = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowDate(new Date())
                .dueDate(calculateDueDate())
                .build();

        BorrowingRecord savedRecord = borrowingRecordRepo.save(borrowingRecord);
        return borrowMapper.toDto(savedRecord);
    }


    @Transactional
    @Override
    @CacheEvict(value = {"patrons","books"},allEntries = true)
    public BoorowDto returnBook(Long bookId, Long patronId) {

        BorrowingRecord borrowingRecord = borrowingRecordRepo.findByBook_BookIdAndPatron_PId(bookId , patronId)
                .orElseThrow(() -> new BookException("Borrowing record not found for bookId: " + bookId + " and patronId: " + patronId));

        borrowingRecord.setReturnDate(new Date());

        BorrowingRecord updatedRecord = borrowingRecordRepo.save(borrowingRecord);

        // Return the DTO representation of the updated BorrowingRecord
        return borrowMapper.toDto(updatedRecord);
    }

    private Date calculateDueDate() {
        // Implement logic to calculate the due date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 14); // Example: 2-week borrowing period
        return calendar.getTime();
    }
}
