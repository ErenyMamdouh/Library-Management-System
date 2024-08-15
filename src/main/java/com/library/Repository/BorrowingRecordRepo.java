package com.library.Repository;

import com.library.Entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBook_BookIdAndPatron_PId(Long bookId, Long patronId);
}
