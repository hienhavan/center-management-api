package org.example.quanlytrungtam.grade;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateGradeRequest {
    private Integer gradeId;
    private Double theoryGrade;
    private Double practicalGrade;

    public Double getAverageGrade() {
        return (theoryGrade + practicalGrade) / 2;
    }
}
