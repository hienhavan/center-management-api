package org.example.quanlytrungtam.grade;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowStudentGradeResponse {
    private String className;
    private String studentName;
    private String subjectName;
    private ShowGradeResponse grade;
}
