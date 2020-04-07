package com.shahid.fashionista_mobile.services;

import com.shahid.fashionista_mobile.api.CartInterface;
import com.shahid.fashionista_mobile.callbacks.CustomCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.dto.request.CartRequest;

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
}
