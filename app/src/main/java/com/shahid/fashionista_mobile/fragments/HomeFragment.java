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
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.callbacks.onItemClickListener;
import com.shahid.fashionista_mobile.databinding.FragmentHomeBinding;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class HomeFragment extends ExpireFragment implements ServiceCallback, onItemClickListener {
    @Inject
    ProductService service;
    private FragmentHomeBinding binding;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<ProductListResponse> productList = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>(null);

    private RecyclerView productView;

    private ProductAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productView = binding.productView;

        loading.observe(getViewLifecycleOwner(), this::onLoadingChange);
        productList.observe(getViewLifecycleOwner(), this::onProductsChange);
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
        getProducts();
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
        productList.setValue((ProductListResponse) mResponse.body());
        loading.setValue(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        error.setValue(mErrorMessage);
        loading.setValue(false);
    }

    @Override
    public void onItemClick(String id) {
        if (auth != null && auth.getRole().equals("ADMIN")) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        rootNavController.navigate(R.id.productFragment, bundle);
    }
}
