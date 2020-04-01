package com.shahid.fashionista_mobile.api;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.dto.response.CustomErrorResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomCallback<T> implements Callback<T> {
    private ServiceCallback mServiceCallback;

    public CustomCallback(ServiceCallback mServiceCallback) {
        this.mServiceCallback = mServiceCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && response.errorBody() != null) {
            String mErrorMessage = new Gson().fromJson(response.errorBody().charStream(), CustomErrorResponse.class).getMessage();
            mServiceCallback.onFailure(mErrorMessage);
        } else if (response.body() != null) {
            mServiceCallback.onSuccess(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t.getMessage() != null) {
            mServiceCallback.onFailure(t.getMessage());
        }
    }
}
