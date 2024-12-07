package org.example.quanlytrungtam.fee;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface NewFindFeeResponse {
    Integer getIdFee();

    String getStudentName();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getDueDate();

    Double getAmount();

    String getStatus();
}
