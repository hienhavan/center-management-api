package org.example.quanlytrungtam.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowGradeResponse {
    private Integer gradeId;

    private Double theoryGrade;

    private Double practicalGrade;

    private Double averageGrade;
}
