package org.example.quanlytrungtam.config.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    @JsonProperty("id")
    private int id;
    @JsonProperty("token")
    private String token;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("profilePicture")
    private String profilePicture;
    @JsonProperty("role")
    private String role;
}


