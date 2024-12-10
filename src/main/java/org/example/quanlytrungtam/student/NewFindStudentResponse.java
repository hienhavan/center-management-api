package org.example.quanlytrungtam.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface NewFindStudentResponse {
    Integer getIdStudent();

    String getFullName();

    String getImg();

    String getEmail();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getDateOfBirth();

    String getAddress();

    String getGender();

    String getPhoneNumber();

    String getStatus();
}
