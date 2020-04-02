package com.shahid.fashionista_mobile.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String id;
    private String name;
    private String role;
    private long expirationInSeconds;
    private Date expirationDate;
}
