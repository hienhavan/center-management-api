package org.example.quanlytrungtam.dailystudent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDailyStudentRequest {
    private Integer studentId;
    private Integer teacherId;
    private String context;
}
