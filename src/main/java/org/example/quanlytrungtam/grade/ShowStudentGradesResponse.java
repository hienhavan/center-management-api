package org.example.quanlytrungtam.grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quanlytrungtam.student.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowStudentGradesResponse {
    private Status status;
    private String studentName;
    private String subjectName;
    private ShowGradeResponse grade;
}
