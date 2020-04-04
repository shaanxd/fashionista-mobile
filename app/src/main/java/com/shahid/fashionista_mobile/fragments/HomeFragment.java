package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.shahid.fashionista_mobile.databinding.FragmentHomeBinding;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeFragment extends ExpireFragment implements ServiceCallback, ItemClickCallback {
    private static final String TAG = "HomeFragment";
    @Inject
    ProductService service;
    private FragmentHomeBinding binding;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<ProductListResponse> products = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>(null);

    private LinearLayout loadingLayout, errorLayout, productsLayout;
    private RecyclerView productRecyclerView;

    private ProductAdapter adapter;
    private GridLayoutManager layoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingLayout = binding.loadingLayout;
        errorLayout = binding.errorLayout;
        productsLayout = binding.productsLayout;

        productRecyclerView = binding.productRecyclerView;

        loading.observe(getViewLifecycleOwner(), value -> {
            if (value) {
                onLoading();
            } else if (error.getValue() != null) {
                onError();
            } else {
                onProductsLoaded();
            }
        });
        getProducts();
    }

    private void onLoading() {
        productsLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        loadingLayout.setVisibility(VISIBLE);
    }

    private void onError() {
        loadingLayout.setVisibility(GONE);
        errorLayout.setVisibility(VISIBLE);
    }

    private void onProductsLoaded() {
        if (products.getValue() != null) {
            loadingLayout.setVisibility(GONE);
            if (layoutManager == null) {
                layoutManager = new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false);
            }
            adapter = new ProductAdapter(products.getValue().getProducts(), this);
            productRecyclerView.setLayoutManager(layoutManager);
            productRecyclerView.setAdapter(adapter);
            productsLayout.setVisibility(VISIBLE);
        }
    }

    private void getProducts() {
        if (loading.getValue() != null && !loading.getValue()) {
            loading.setValue(true);
        }
        if (error.getValue() != null) {
            error.setValue(null);
        }
        service.getProducts(0, 8, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        ProductListResponse response = (ProductListResponse) mResponse.body();
        products.setValue(response);
        loading.setValue(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        error.setValue(mErrorMessage);
        loading.setValue(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loading.removeObservers(this);
    }

    @Override
    public void onItemClick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        rootNavController.navigate(R.id.action_navigationFragment_to_productFragment, bundle);
    }
}
