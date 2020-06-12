package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.ProductInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.request.CategoryRequest;
import com.shahid.fashionista_mobile.dto.request.InquiryRequest;
import com.shahid.fashionista_mobile.dto.request.ProductRequest;
import com.shahid.fashionista_mobile.dto.request.ProductTagRequest;
import com.shahid.fashionista_mobile.dto.request.ReplyRequest;
import com.shahid.fashionista_mobile.dto.request.ReviewRequest;
import com.shahid.fashionista_mobile.dto.response.AllTagsResponse;
import com.shahid.fashionista_mobile.dto.response.FavouriteResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryResponse;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ProductService {
    private ProductInterface api;

    @Inject
    public ProductService(Retrofit retrofit) {
        this.api = retrofit.create(ProductInterface.class);
    }

    public void getProducts(int page, int size, ServiceCallback callback) {
        Call<ProductListResponse> call = api.getProducts(page, size);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProduct(String id, ServiceCallback callback) {
        Call<ProductResponse> call = api.getProduct(id);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProductReviews(String id, int page, int size, ServiceCallback callback) {
        Call<ReviewListResponse> call = api.getProductReviews(id, page, size, "updatedAt,desc");
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProductInquiries(String id, int page, int size, ServiceCallback callback) {
        Call<InquiryListResponse> call = api.getProductInquiries(id, page, size, "updatedAt,desc");
        call.enqueue(new CustomCallback<>(callback));
    }

    public void addReview(String id, String token, ReviewRequest request, ServiceCallback callback) {
        Call<ProductResponse> call = api.addReview(id, request, token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void addInquiry(String id, String token, InquiryRequest request, ServiceCallback callback) {
        Call<InquiryListResponse> call = api.addInquiry(id, request, token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getFavourites(String token, ServiceCallback callback) {
        Call<ProductListResponse> call = api.getFavourites(token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProductFavourite(String id, String token, ServiceCallback callback) {
        Call<FavouriteResponse> call = api.getProductFavourite(id, token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void toggleProductFavourite(String id, String token, ServiceCallback callback) {
        Call<FavouriteResponse> call = api.toggleProductFavourite(id, token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProductTags(ServiceCallback callback) {
        Call<AllTagsResponse> call = api.getAllProductTags();
        call.enqueue(new CustomCallback<>(callback));
    }

    public void createCategory(String token, CategoryRequest request, ServiceCallback callback) {
        Call<Object> call = api.createCategory(
                token,
                request.getImage(),
                request.getName(),
                request.getDescription(),
                request.getType()
        );
        call.enqueue(new CustomCallback<>(callback));
    }

    public void createProduct(String token, ProductRequest request, ServiceCallback callback) {
        Call<Object> call = api.createProduct(
                token,
                request.getThumbnail(),
                request.getImages(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock(),
                request.getTags()
        );
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getAllInquiries(String token, int page, ServiceCallback callback) {
        Call<InquiryListResponse> call = api.getAllInquiries(token, page, 10, "updatedAt,desc");
        call.enqueue(new CustomCallback<>(callback));
    }

    public void addReply(String id, String token, ReplyRequest reply, ServiceCallback callback) {
        Call<InquiryResponse> call = api.addReply(id, token, reply);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getProductsByTag(ProductTagRequest request, int page, ServiceCallback callback) {
        Call<ProductListResponse> call = api.getProductsByType(request, page, 1);
        call.enqueue(new CustomCallback<>(callback));
    }
}
