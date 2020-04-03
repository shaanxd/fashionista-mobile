package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.response.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductInterface {
    @GET("api/products")
    Call<ProductListResponse> getProducts(@Query("page") int page, @Query("size") int size);
}
