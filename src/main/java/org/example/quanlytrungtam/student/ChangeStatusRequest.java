package org.example.quanlytrungtam.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeStatusRequest {
    private Integer studentId;
    private String status;
}
