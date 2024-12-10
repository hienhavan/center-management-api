package org.example.quanlytrungtam.dailystudent;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface NewfindListDailyStudentResponse {
    Integer getIdDaily();

    String getNameTeacher();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDueDate();

    String getContext();
}