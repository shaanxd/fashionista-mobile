package com.shahid.fashionista_mobile.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.dto.response.AuthResponse;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefStore {
    private final String USER_FILE = "USER_FILE";
    private final String USER_INFO = "USER_INFO";
    private Context context;

    @Inject
    public SharedPrefStore(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreferences(String FILE_KEY, int FILE_MODE) {
        return context.getSharedPreferences(FILE_KEY, FILE_MODE);
    }

    public AuthResponse getAuthSharedPreferences() {
        SharedPreferences sharedPrefs = getSharedPreferences(USER_FILE, MODE_PRIVATE);
        String authString = sharedPrefs.getString(USER_INFO, null);
        if (authString == null) {
            return null;
        }
        return new Gson().fromJson(authString, AuthResponse.class);
    }

    public void setAuthSharedPreferences(AuthResponse authObj) {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        String authString = new Gson().toJson(authObj);
        prefsEditor.putString(USER_INFO, authString).apply();
    }

    public void clearAuthSharedPreferences() {
        SharedPreferences.Editor prefsEditor = getSharedPreferences(USER_FILE, MODE_PRIVATE).edit();
        prefsEditor.clear().apply();
    }
}
