package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.AuthRequest;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;
import com.shahid.fashionista_mobile.dto.response.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthInterface {
    @POST("api/auth/login")
    Call<AuthResponse> signIn(@Body AuthRequest request);

    @POST("api/auth/register")
    Call<AuthResponse> signUp(@Body SignUpRequest request);
}
