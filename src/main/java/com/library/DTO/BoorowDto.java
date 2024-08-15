package com.library.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoorowDto {
    private Long recordId;

    @NotNull(message = "book ID cannot be null")
    private Long bookId;

    @NotNull(message = "Patron ID cannot be null")
    private Long PId;

    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
}
