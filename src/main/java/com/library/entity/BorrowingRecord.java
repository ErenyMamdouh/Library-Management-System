package com.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "PId", nullable = false)
    private Patron patron;

    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    public static BorrowingRecordBuilder builder() {
        return new BorrowingRecordBuilder();
    }
    public static class BorrowingRecordBuilder {
        private Book book;
        private Patron patron;
        private Date borrowDate;
        private Date dueDate;

        public BorrowingRecordBuilder book(Book book) {
            this.book = book;
            return this;
        }

        public BorrowingRecordBuilder patron(Patron patron) {
            this.patron = patron;
            return this;
        }

        public BorrowingRecordBuilder borrowDate(Date borrowDate) {
            this.borrowDate = borrowDate;
            return this;
        }

        public BorrowingRecordBuilder dueDate(Date dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public BorrowingRecord build() {
            BorrowingRecord record = new BorrowingRecord();
            record.setBook(this.book);
            record.setPatron(this.patron);
            record.setBorrowDate(this.borrowDate);
            record.setDueDate(this.dueDate);
            return record;
        }

    }
}
