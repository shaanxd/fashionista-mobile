package com.shahid.fashionista_mobile.api;

import com.shahid.fashionista_mobile.dto.request.InquiryRequest;
import com.shahid.fashionista_mobile.dto.request.ReplyRequest;
import com.shahid.fashionista_mobile.dto.request.ReviewRequest;
import com.shahid.fashionista_mobile.dto.response.AllTagsResponse;
import com.shahid.fashionista_mobile.dto.response.FavouriteResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryResponse;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductInterface {
    @GET("api/products")
    Call<ProductListResponse> getProducts(@Query("page") int page, @Query("size") int size);

    @GET("api/products/product/{id}")
    Call<ProductResponse> getProduct(@Path("id") String id);

    @GET("api/products/reviews/{id}")
    Call<ReviewListResponse> getProductReviews(@Path("id") String id, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @GET("api/products/inquiries/{id}")
    Call<InquiryListResponse> getProductInquiries(@Path("id") String id, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

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

    @GET("/api/tags/all")
    Call<AllTagsResponse> getAllProductTags();

    @Multipart
    @POST("/api/admin/create-tag")
    Call<Object> createCategory(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("type") RequestBody type
    );

    @Multipart
    @POST("/api/admin/create-product")
    Call<Object> createProduct(
            @Header("Authorization") String token,
            @Part MultipartBody.Part thumbnail,
            @Part List<MultipartBody.Part> images,
            @Part("name") RequestBody name,
            @Part("description") RequestBody description,
            @Part("price") RequestBody price,
            @Part("stock") RequestBody stock,
            @Part List<MultipartBody.Part> tags
    );

    @GET("/api/admin/all-inquiries")
    Call<InquiryListResponse> getAllInquiries(
            @Header("Authorization") String token,
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

    @POST("/api/products/add-reply/{id}")
    Call<InquiryResponse> addReply(@Path("id") String id, @Header("Authorization") String token, @Body ReplyRequest request);
}
