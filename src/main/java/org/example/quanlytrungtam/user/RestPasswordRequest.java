package org.example.quanlytrungtam.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestPasswordRequest {
    private String code;
    private String newPassword;
}
