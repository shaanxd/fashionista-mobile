package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.CartRequest;
import com.shahid.fashionista_mobile.dto.request.PurchaseRequest;
import com.shahid.fashionista_mobile.dto.response.CartResponse;
import com.shahid.fashionista_mobile.dto.response.PurchaseListResponse;
import com.shahid.fashionista_mobile.dto.response.PurchaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartInterface {
    @POST("api/cart/add-product")
    Call<Object> addToCart(@Header("Authorization") String token, @Body CartRequest request);

    @GET("api/cart")
    Call<CartResponse> getCart(@Header("Authorization") String token);

    @POST("api/cart/delete-cart/{id}")
    Call<CartResponse> deleteCart(@Header("Authorization") String token, @Path("id") String id);

    @GET("api/purchases")
    Call<PurchaseListResponse> getPurchases(@Header("Authorization") String token, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @POST("api/purchases/purchase-cart")
    Call<PurchaseResponse> purchaseCart(@Header("Authorization") String token, @Body PurchaseRequest request);
}
