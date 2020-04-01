package com.shahid.fashionista_mobile.api;

import retrofit2.Response;

public interface ServiceCallback {
    void onSuccess(Response mResponse);
    void onFailure(String mErrorMessage);
}
