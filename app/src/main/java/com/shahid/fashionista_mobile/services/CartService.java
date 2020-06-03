package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.CartInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.request.CartRequest;
import com.shahid.fashionista_mobile.dto.request.PurchaseRequest;
import com.shahid.fashionista_mobile.dto.response.CartResponse;
import com.shahid.fashionista_mobile.dto.response.PurchaseListResponse;
import com.shahid.fashionista_mobile.dto.response.PurchaseResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Retrofit;

public class CartService {
    private CartInterface api;

    @Inject
    public CartService(Retrofit retrofit) {
        this.api = retrofit.create(CartInterface.class);
    }

    public void addToCart(String token, CartRequest request, ServiceCallback callback) {
        Call<Object> call = api.addToCart(token, request);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getCart(String token, ServiceCallback callback) {
        Call<CartResponse> call = api.getCart(token);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void deleteCart(String token, String id, ServiceCallback callback) {
        Call<CartResponse> call = api.deleteCart(token, id);
        call.enqueue(new CustomCallback<>(callback));
    }

    public void getPurchases(String token, int pageNumber, ServiceCallback callback) {
        Call<PurchaseListResponse> call = api.getPurchases(token, pageNumber, 8, "createdAt,desc");
        call.enqueue(new CustomCallback<>(callback));
    }

    public void purchaseCart(String token, PurchaseRequest request, ServiceCallback callback) {
        Call<PurchaseResponse> call = api.purchaseCart(token, request);
        call.enqueue(new CustomCallback<>(callback));
    }
}
