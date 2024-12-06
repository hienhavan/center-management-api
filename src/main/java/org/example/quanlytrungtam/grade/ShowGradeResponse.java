package org.example.quanlytrungtam.grade;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowGradeResponse {
    private Double theoryGrade;

    private Double practicalGrade;

    private Double averageGrade;
}
