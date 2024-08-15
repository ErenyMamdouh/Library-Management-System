package com.library.Service;

import com.library.DTO.BoorowDto;

public interface BorrowService {
    BoorowDto borrowBook(Long bookId, Long patronId);

    BoorowDto returnBook(Long bookId, Long patronId);

}
