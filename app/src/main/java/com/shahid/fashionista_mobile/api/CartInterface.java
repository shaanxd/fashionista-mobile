package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.CartRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CartInterface {
    @POST("api/cart/add-product")
    Call<Object> addToCart(@Header("Authorization") String token, @Body CartRequest request);
}
