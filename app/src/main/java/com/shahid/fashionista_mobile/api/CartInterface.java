package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.CartRequest;
import com.shahid.fashionista_mobile.dto.response.CartResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartInterface {
    @POST("api/cart/add-product")
    Call<Object> addToCart(@Header("Authorization") String token, @Body CartRequest request);

    @GET("api/cart")
    Call<CartResponse> getCart(@Header("Authorization") String token);

    @POST("api/cart/delete-cart/{id}")
    Call<CartResponse> deleteCart(@Header("Authorization") String token, @Path("id") String id);
}
