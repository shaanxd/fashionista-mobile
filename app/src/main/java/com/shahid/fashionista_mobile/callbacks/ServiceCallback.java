package com.shahid.fashionista_mobile.callbacks;

import retrofit2.Response;

public interface ServiceCallback {
    void onSuccess(Response mResponse);
    void onFailure(String mErrorMessage);
}
