package com.library.Controller;

import com.library.DTO.BoorowDto;
import com.library.Service.imp.BorrowServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowController {

    @Autowired
    private BorrowServiceimp borrowServiceimp;

    // Endpoint to allow a patron to borrow a book
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BoorowDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BoorowDto boorowDto = borrowServiceimp.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(boorowDto);
    }

    // Endpoint to record the return of a borrow book
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BoorowDto> returnBook(@PathVariable Long bookId,@PathVariable Long patronId) {
        BoorowDto boorowDto = borrowServiceimp.returnBook(bookId, patronId);
        return ResponseEntity.ok(boorowDto);
    }
}
