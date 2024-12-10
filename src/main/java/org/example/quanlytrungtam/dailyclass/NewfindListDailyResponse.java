package org.example.quanlytrungtam.dailyclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public interface NewfindListDailyResponse {
    Integer getIdDaily();

    String getNameTeacher();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getDueDate();

    String getContext();
}
