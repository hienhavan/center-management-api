package org.example.quanlytrungtam.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FindUserResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Boolean active;
    private String img;
    private LocalDate dateOfBirth;
    private String address;
}