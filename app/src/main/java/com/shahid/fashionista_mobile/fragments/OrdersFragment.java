package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.PurchaseAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentOrdersBinding;
import com.shahid.fashionista_mobile.dto.response.PurchaseListResponse;
import com.shahid.fashionista_mobile.services.CartService;

import javax.inject.Inject;

import retrofit2.Response;

public class OrdersFragment extends AuthFragment implements ServiceCallback {

    @Inject
    CartService cartService;

    PurchaseAdapter adapter;

    RecyclerView orders;

    private FragmentOrdersBinding binding;

    int current = 0;
    int total = 0;
    int size = 0;

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

        binding.setLoading(true);

        orders = binding.orders;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);

        getOrders(current);
    }

    private void onPreviousClick(View view) {
        if (current != 0) {
            getOrders(current - 1);
        }
    }

    private void onNextClick(View view) {
        if (current != total) {
            getOrders(current + 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders(current);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.setLoading(true);
    }

    private void getOrders(int page) {
        if (auth == null) {
            return;
        }
        cartService.getPurchases("Bearer " + auth.getToken(), page, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        PurchaseListResponse page = (PurchaseListResponse) mResponse.body();
        if (page == null) {
            return;
        }

        current = page.getCurrent();
        total = page.getTotal();
        size = page.getPurchases().size();

        binding.setCurrent(current);
        binding.setTotal(total);
        binding.setSize(size);

        if (adapter == null) {
            adapter = new PurchaseAdapter(page.getPurchases(), this::onItemClick);
        } else {
            adapter.setPurchases(page.getPurchases());
        }
        orders.setAdapter(adapter);

        binding.setLoading(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setError(mErrorMessage);
        binding.setLoading(false);
    }

    public void onItemClick(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("ORDER_DETAILS", id);
        rootNavController.navigate(R.id.action_navigationFragment_to_orderFragment, bundle);
    }
}
