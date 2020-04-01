package com.shahid.fashionista_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
