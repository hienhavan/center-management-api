package org.example.quanlytrungtam.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowStudentGradeResponse {
    //    private String status;
    private String className;
    private String studentName;
    private String subjectName;
    private ShowGradeResponse grade;
}
