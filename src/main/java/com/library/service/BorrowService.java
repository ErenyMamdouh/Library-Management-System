package com.library.service;

import com.library.dto.BoorowDto;

public interface BorrowService {
    BoorowDto borrowBook(Long bookId, Long patronId);

    BoorowDto returnBook(Long bookId, Long patronId);

}
