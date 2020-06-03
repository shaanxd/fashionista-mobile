package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.InquiryRequest;
import com.shahid.fashionista_mobile.dto.request.ReviewRequest;
import com.shahid.fashionista_mobile.dto.response.FavouriteResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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

    @POST("/api/products/add-review/{id}")
    Call<ProductResponse> addReview(@Path("id") String id, @Body ReviewRequest request, @Header("Authorization") String token);

    @POST("/api/products/add-inquiry/{id}")
    Call<InquiryListResponse> addInquiry(@Path("id") String id, @Body InquiryRequest request, @Header("Authorization") String token);

    @GET("/api/favourites")
    Call<ProductListResponse> getFavourites(@Header("Authorization") String token);

    @GET("/api/favourites/is-favourite/{id}")
    Call<FavouriteResponse> getProductFavourite(@Path("id") String id, @Header("Authorization") String token);

    @POST("/api/favourites/toggle/{id}")
    Call<FavouriteResponse> toggleProductFavourite(@Path("id") String id, @Header("Authorization") String token);
}
