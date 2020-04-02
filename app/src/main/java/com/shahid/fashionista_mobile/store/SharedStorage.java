package com.shahid.fashionista_mobile.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class SharedStorage {
    private final String USER_FILE = "USER_FILE";
    private final String USER_INFO = "USER_INFO";
    private Context context;

    @Inject
    public SharedStorage(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreferences(String key, int mode) {
        return context.getSharedPreferences(key, mode);
    }

    public AuthenticationResponse getAuthSharedPreferences() {
        SharedPreferences sharedPrefs = getSharedPreferences(USER_FILE, MODE_PRIVATE);
        String authString = sharedPrefs.getString(USER_INFO, null);
        if (authString == null) {
            return null;
        }
        return new Gson().fromJson(authString, AuthenticationResponse.class);
    }

    public void setAuthSharedPreferences(AuthenticationResponse obj) {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        String authString = new Gson().toJson(obj);
        prefsEditor.putString(USER_INFO, authString).apply();
    }

    public void clearAuthSharedPreferences() {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        prefsEditor.clear().apply();
    }
}
