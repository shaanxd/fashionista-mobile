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
    private Retrofit retrofit;

    @Inject
    public AuthenticationService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void signInUser(AuthenticationRequest request, ServiceCallback callback) {
        AuthInterface auth = retrofit.create(AuthInterface.class);
        Call<AuthenticationResponse> call = auth.signIn(request);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void signUpUser(SignUpRequest request, ServiceCallback callback) {
        AuthInterface api = retrofit.create(AuthInterface.class);
        Call<AuthenticationResponse> call = api.signUp(request);
        call.enqueue(new CustomCallback<>(callback));
    }

}
