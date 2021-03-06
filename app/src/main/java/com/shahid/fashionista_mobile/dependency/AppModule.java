package com.shahid.fashionista_mobile.dependency;

import android.content.Context;

import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.services.AuthenticationService;
import com.shahid.fashionista_mobile.services.CartService;
import com.shahid.fashionista_mobile.services.ProductService;
import com.shahid.fashionista_mobile.store.SessionStorage;
import com.shahid.fashionista_mobile.store.SharedStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shahid.fashionista_mobile.constants.Constants.BASE_URL;

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
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AuthenticationService getAuthenticationServiceInstance(Retrofit retrofit) {
        return new AuthenticationService(retrofit);
    }

    @Provides
    @Singleton
    public ProductService getProductServiceInstance(Retrofit retrofit) {
        return new ProductService(retrofit);
    }

    @Provides
    @Singleton
    public SharedStorage getSharedPrefInstance(Context context) {
        return new SharedStorage(context);
    }

    @Provides
    @Singleton
    public SessionStorage getAuthStoreInstance(SharedStorage sharedStorage) {
        return new SessionStorage(sharedStorage);
    }

    @Nullable
    @Provides
    public AuthenticationResponse getAuthentication(SessionStorage sessionStorage) {
        return sessionStorage.getSession();
    }

    @Provides
    @Singleton
    public CartService getCartServiceInstance(Retrofit retrofit) {
        return new CartService(retrofit);
    }
}
