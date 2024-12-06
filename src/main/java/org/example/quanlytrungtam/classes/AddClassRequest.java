package org.example.quanlytrungtam.classes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddClassRequest {
    private String className;
    private Integer teacherId;
}
