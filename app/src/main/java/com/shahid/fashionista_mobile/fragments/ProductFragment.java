package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.SizeButtonAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentProductBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import java.util.Arrays;

import javax.inject.Inject;

import it.sephiroth.android.library.numberpicker.NumberPicker;
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

    private SizeButtonAdapter adapter;
    private NumberPicker quantityPicker;

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
        quantityPicker = binding.quantityPicker;

        binding.addToCartBtn.setOnClickListener(this::onAddToCartClick);

        loading.observe(getViewLifecycleOwner(), this::onLoadingStateChange);
        productService.getProduct(productId, this);
    }

    private void onAddToCartClick(View view) {
        int quantity = quantityPicker.getProgress();
        int stock = product.getValue().getStock();

        if (quantity == 0) {
            DynamicToast.makeWarning(activity, "Quantity cannot be zero").show();
            return;
        } else if (quantity > stock) {
            DynamicToast.makeWarning(activity, "Only " + stock + " pieces available.").show();
            return;
        }
        if (adapter.getSelectedSize() == null) {
            DynamicToast.makeWarning(activity, "Please select a size.").show();
            return;
        }
        // Do API Call
        System.out.println("=== API CALL ===");
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
            Glide.with(binding.getRoot())
                    .load(response.getThumbnail())
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.thumbnailImage);

            // Set product related data here
            binding.setProduct(response);

            if (adapter == null) {
                adapter = new SizeButtonAdapter(Arrays.asList("S", "M", "L", "XL"));
            }
            binding.sizeButtonList.setAdapter(adapter);

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
