package org.example.quanlytrungtam.fee;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddFeeRequest {
    private Integer studentId;
    private Double amount;
    private LocalDate dueDate;
}
