package org.example.quanlytrungtam.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddStudentRequest {
    private Integer userId;
    private Integer classId;
    private String status;
}
