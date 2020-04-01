package com.shahid.fashionista_mobile;

import android.app.Application;

import com.shahid.fashionista_mobile.dependency.AppComponent;
import com.shahid.fashionista_mobile.dependency.AppModule;
import com.shahid.fashionista_mobile.dependency.DaggerAppComponent;

public class FashionApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
