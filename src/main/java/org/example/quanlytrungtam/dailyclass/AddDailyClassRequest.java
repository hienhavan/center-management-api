package org.example.quanlytrungtam.dailyclass;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddDailyClassRequest {
    private Integer idClass;
    private Integer teacherId;
    private String context;
}
