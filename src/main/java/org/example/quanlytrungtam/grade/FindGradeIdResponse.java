package org.example.quanlytrungtam.grade;

import lombok.Builder;
import lombok.Data;
import org.example.quanlytrungtam.student.Status;

@Data
@Builder
public class FindGradeIdResponse {
    private String className;
    private String studentName;
    private String subjectName;
    private ShowGradeResponse grade;
}
