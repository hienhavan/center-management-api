package org.example.quanlytrungtam.dailyclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface NewfindListDailyClassResponse {
    Integer getIdDaily();

    String getNameTeacher();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getDueDate();

    String getContext();
}
