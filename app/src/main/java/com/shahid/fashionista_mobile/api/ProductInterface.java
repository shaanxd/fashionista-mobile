package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductInterface {
    @GET("api/products")
    Call<ProductListResponse> getProducts(@Query("page") int page, @Query("size") int size);

    @GET("api/products/product/{id}")
    Call<ProductResponse> getProduct(@Path("id") String id);
}
