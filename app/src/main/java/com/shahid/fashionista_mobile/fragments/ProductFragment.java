package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentProductBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.services.ProductService;
import com.shahid.fashionista_mobile.utils.ProductUtils;

import javax.inject.Inject;

import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ProductFragment extends RootFragment implements ServiceCallback {
    private static final String TAG = "ProductFragment";

    @Inject
    ProductService productService;
    FragmentProductBinding binding;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<String> error = new MutableLiveData<>(null);
    private MutableLiveData<ProductResponse> product = new MutableLiveData<>(null);
    private String productId;

    private LinearLayout errorLayout, productLayout, loadingLayout;
    private TextView productName;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
        Bundle args = getArguments();
        if (args != null) {
            productId = args.getString("PRODUCT_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        errorLayout = binding.errorLayout;
        loadingLayout = binding.loadingLayout;
        productLayout = binding.productLayout;

        productName = binding.productName;

        loading.observe(getViewLifecycleOwner(), this::onLoadingStateChange);
        productService.getProduct(productId, this);
    }

    private void onLoadingStateChange(Boolean value) {
        if (value) {
            onLoading();
        } else if (error.getValue() != null) {
            onError();
        } else {
            onProductLoaded();
        }
    }

    private void onProductLoaded() {
        if (product.getValue() != null) {
            ProductResponse response = product.getValue();

            loadingLayout.setVisibility(GONE);

            // Set image
            String thumbnail = ProductUtils.getProductImageURL(response.getThumbnail());
            Glide.with(binding.getRoot())
                    .load(thumbnail)
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.thumbnailImage);

            // Set product related data here
            binding.setProduct(response);

            productLayout.setVisibility(VISIBLE);
        }
    }

    private void onError() {
        loadingLayout.setVisibility(GONE);
        errorLayout.setVisibility(VISIBLE);
    }

    private void onLoading() {
        if (loading.getValue() != null && !loading.getValue()) {
            loading.setValue(true);
        }
        if (error.getValue() != null) {
            error.setValue(null);
        }
        productLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
        loadingLayout.setVisibility(VISIBLE);
    }

    @Override
    public void onSuccess(Response mResponse) {
        product.setValue((ProductResponse) mResponse.body());
        loading.setValue(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        error.setValue(mErrorMessage);
        loading.setValue(false);
    }
}
