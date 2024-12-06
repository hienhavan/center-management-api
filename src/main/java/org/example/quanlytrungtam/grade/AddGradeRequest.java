package org.example.quanlytrungtam.grade;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddGradeRequest {
    private Integer studentId;
    private Integer subjectId;
    private Double theoryGrade;
    private Double practicalGrade;
}
