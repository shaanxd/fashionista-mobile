package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.ProductAdapter;
import com.shahid.fashionista_mobile.callbacks.ItemClickCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentFavouritesBinding;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class FavouritesFragment extends AuthFragment implements ServiceCallback, ItemClickCallback {

    @Inject
    ProductService service;

    private FragmentFavouritesBinding binding;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<ProductListResponse> products = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>(null);

    private RecyclerView productView;

    private ProductAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productView = binding.productView;

        loading.observe(getViewLifecycleOwner(), this::onLoadingChange);
        products.observe(getViewLifecycleOwner(), this::onProductsChange);
        error.observe(getViewLifecycleOwner(), this::onErrorChange);
    }

    @Override
    public void onPause() {
        super.onPause();
        loading.setValue(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavouritesFromApi();
    }

    private void getFavouritesFromApi() {
        loading.setValue(true);
        error.setValue(null);
        if (authState == null) {
            return;
        }
        service.getFavourites("Bearer " + authState.getToken(), this);
    }

    private void onLoadingChange(Boolean loading) {
        binding.setLoading(loading);
    }

    private void onErrorChange(String error) {
        binding.setError(error);
    }

    private void onProductsChange(ProductListResponse products) {
        binding.setProducts(products);

        if (products != null && products.getProducts().size() != 0) {
            if (productView.getLayoutManager() == null) {
                productView.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false));
            }
            adapter = new ProductAdapter(products.getProducts(), this);
            productView.setAdapter(adapter);
        }
    }

    @Override
    public void onSuccess(Response mResponse) {
        products.setValue((ProductListResponse) mResponse.body());
        loading.setValue(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        error.setValue(mErrorMessage);
        loading.setValue(false);
    }

    @Override
    public void onItemClick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        rootNavController.navigate(R.id.action_navigationFragment_to_productFragment, bundle);
    }
}
