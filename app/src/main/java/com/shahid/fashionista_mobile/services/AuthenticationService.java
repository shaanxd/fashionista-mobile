package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.dto.request.AuthenticationRequest;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class AuthenticationService {
    private Retrofit retrofit;

    @Inject
    public AuthenticationService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void signInUser(AuthenticationRequest request) {

    }

    public void signUpUser(SignUpRequest request) {

    }

}
