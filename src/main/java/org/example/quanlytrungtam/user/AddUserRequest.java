package org.example.quanlytrungtam.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Builder
public class AddUserRequest {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Boolean active;
    private Set<Role> roles;

}