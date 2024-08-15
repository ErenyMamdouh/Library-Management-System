package com.library.Service.imp;

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
import com.library.Service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BoorowDto returnBook(Long bookId, Long patronId) {
        // Retrieve the BorrowingRecord from the repository
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
