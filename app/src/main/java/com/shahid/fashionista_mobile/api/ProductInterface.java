package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductInterface {
    @GET("api/products")
    Call<ProductListResponse> getProducts(@Query("page") int page, @Query("size") int size);

    @GET("api/products/product/{id}")
    Call<ProductResponse> getProduct(@Path("id") String id);

    @GET("api/products/reviews/{id}")
    Call<ReviewListResponse> getProductReviews(@Path("id") String id, @Query("page") int page, @Query("size") int size);

    @GET("api/products/inquiries/{id}")
    Call<InquiryListResponse> getProductInquiries(@Path("id") String id, @Query("page") int page, @Query("size") int size);
}
