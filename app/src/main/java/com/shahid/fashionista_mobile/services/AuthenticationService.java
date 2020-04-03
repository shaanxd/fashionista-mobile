package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.AuthInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.request.AuthenticationRequest;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class AuthenticationService {
    private AuthInterface api;

    @Inject
    public AuthenticationService(Retrofit retrofit) {
        this.api = retrofit.create(AuthInterface.class);
    }

    public void signInUser(AuthenticationRequest request, ServiceCallback callback) {
        Call<AuthenticationResponse> call = api.signIn(request);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void signUpUser(SignUpRequest request, ServiceCallback callback) {
        Call<AuthenticationResponse> call = api.signUp(request);
        call.enqueue(new CustomCallback<>(callback));
    }

}
