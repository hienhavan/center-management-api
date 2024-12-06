package org.example.quanlytrungtam.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePasswordRequest {
    private String currentPassword;
    private String newPassword;
}