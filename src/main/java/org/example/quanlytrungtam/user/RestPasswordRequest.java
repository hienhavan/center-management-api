package org.example.quanlytrungtam.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class RestPasswordRequest {
    private String code;
    private String newPassword;
}
