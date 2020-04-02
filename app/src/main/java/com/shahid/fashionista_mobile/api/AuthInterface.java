package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.AuthenticationRequest;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {
    @POST("api/auth/login")
    Call<AuthenticationResponse> signIn(@Body AuthenticationRequest request);

    @POST("api/auth/register")
    Call<AuthenticationResponse> signUp(@Body SignUpRequest request);
}
