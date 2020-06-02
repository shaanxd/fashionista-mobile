package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.PurchaseAdapter;
import com.shahid.fashionista_mobile.callbacks.ItemClickCallback;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentOrdersBinding;
import com.shahid.fashionista_mobile.dto.response.PurchaseListResponse;
import com.shahid.fashionista_mobile.services.CartService;

import javax.inject.Inject;

import retrofit2.Response;

public class OrdersFragment extends AuthFragment implements ServiceCallback, ItemClickCallback {

    @Inject
    CartService cartService;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<String> error = new MutableLiveData<>(null);
    private MutableLiveData<PurchaseListResponse> purchases = new MutableLiveData<>(null);
    private FragmentOrdersBinding binding;

    public OrdersFragment() {
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
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loading.observe(getViewLifecycleOwner(), this::onLoadingChange);
        error.observe(getViewLifecycleOwner(), this::onErrorChange);
        purchases.observe(getViewLifecycleOwner(), this::onPurchaseChange);
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrdersFromApi();
    }

    @Override
    public void onPause() {
        super.onPause();
        loading.setValue(true);
    }

    private void onPurchaseChange(PurchaseListResponse purchases) {
        binding.setOrders(purchases);

        if (purchases != null && purchases.getPurchases().size() > 0) {
            PurchaseAdapter adapter = new PurchaseAdapter(purchases.getPurchases(), this);
            binding.purchasesView.setAdapter(adapter);
        }
    }

    private void onErrorChange(String error) {
        binding.setError(error);
    }

    private void onLoadingChange(Boolean loading) {
        binding.setLoading(loading);
    }

    private void getOrdersFromApi() {
        Boolean currentLoading = loading.getValue();
        if (currentLoading == null || !currentLoading) {
            loading.setValue(true);
        }
        String currentError = error.getValue();
        if (currentError != null) {
            error.setValue(null);
        }
        if (authState == null) {
            return;
        }
        cartService.getPurchases("Bearer " + authState.getToken(), 0, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        PurchaseListResponse responseBody = (PurchaseListResponse) mResponse.body();

        if (responseBody == null) {
            return;
        }

        purchases.setValue(responseBody);
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
        bundle.putString("ORDER_DETAILS", id);
        rootNavController.navigate(R.id.action_navigationFragment_to_orderFragment, bundle);
    }
}
