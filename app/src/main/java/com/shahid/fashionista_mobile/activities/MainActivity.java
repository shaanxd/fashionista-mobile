package com.shahid.fashionista_mobile.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((FashionApp)getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("[MAIN_ACTIVITY]", retrofit.toString());
    }
}
