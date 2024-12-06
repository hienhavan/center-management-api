package org.example.quanlytrungtam.subject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddSubjectRequest {
    private String subjectName;
}
