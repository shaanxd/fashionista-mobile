package com.shahid.fashionista_mobile.activities;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.store.AuthStore;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TimerCallback, Runnable {
    @Inject
    AuthStore authStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((FashionApp) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void start() {
        new Handler().postDelayed(this, 10000);
    }

    @Override
    public void run() {
        authStore.setAuthentication(null);
    }
}
