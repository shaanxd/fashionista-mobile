package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.AuthInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.request.AuthRequest;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;
import com.shahid.fashionista_mobile.dto.response.AuthResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AuthenticationService {
    private Retrofit retrofit;

    @Inject
    public AuthenticationService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void signInUser(AuthRequest request, ServiceCallback serviceCallback) {
        AuthInterface authInterface = retrofit.create(AuthInterface.class);
        Call<AuthResponse> authResponseCall = authInterface.signIn(request);
        authResponseCall.enqueue(new CustomCallback<>(serviceCallback));
    }

    public void signUpUser(SignUpRequest request) {

    }

}
