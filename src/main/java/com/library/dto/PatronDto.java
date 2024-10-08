package com.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatronDto {

    private Long pId;

    @NotBlank(message = "name is reqyired")
    private String fullName;

    @Email
    private String email;
    private String phoneNumber;
    private String address;

}
