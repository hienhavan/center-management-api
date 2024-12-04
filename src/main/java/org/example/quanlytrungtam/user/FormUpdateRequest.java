package org.example.quanlytrungtam.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
public class FormUpdateRequest {
    private String fullName;
    private Gender gender;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private Boolean active;
    private MultipartFile img;
}