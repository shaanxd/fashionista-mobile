package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.ProductInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;

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
}
