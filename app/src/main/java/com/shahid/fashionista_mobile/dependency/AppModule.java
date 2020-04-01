package com.shahid.fashionista_mobile.dependency;

import android.app.Application;
import android.content.Context;

import com.shahid.fashionista_mobile.services.AuthenticationService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context getApplicationContext() {
        return context;
    }

    @Provides
    @Singleton
    public Retrofit getRetroFitInstance() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AuthenticationService getAuthenticationServiceInstance(Retrofit retrofit) {
        return new AuthenticationService(retrofit);
    }

}
