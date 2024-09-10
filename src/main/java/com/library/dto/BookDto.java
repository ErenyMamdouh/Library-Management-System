package com.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long bookId;
    @NotBlank(message = "Book title is reqyired")
    private String title;
    private String auther;
    private Date publicationDate;
    private String ISBN;

    //private boolean available = true;

}
