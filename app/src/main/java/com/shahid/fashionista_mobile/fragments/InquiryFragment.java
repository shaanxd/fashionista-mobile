package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.InquiryAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentInquiryBinding;
import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class InquiryFragment extends RootFragment implements ServiceCallback {
    FragmentInquiryBinding binding;
    ProductResponse product;
    RecyclerView inquiryList;
    InquiryAdapter adapter;

    int current = 0;
    int total = 0;
    int size = 0;

    @Inject
    ProductService service;

    public InquiryFragment(ProductResponse product) {
        this.product = product;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInquiryBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inquiryList = binding.inquiryList;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);

        //Set Inquiry data
        setInquiriesToLayout(product.getInquiries());
    }

    private void setInquiriesToLayout(InquiryListResponse page) {
        current = page.getCurrent();
        total = page.getTotal();
        size = page.getInquiries().size();

        binding.setCurrent(current);
        binding.setTotal(total);
        binding.setSize(size);

        if (adapter == null) {
            adapter = new InquiryAdapter(page.getInquiries());
            inquiryList.setAdapter(adapter);
        } else {
            adapter.setReviews(page.getInquiries());
        }

        binding.setLoading(false);
    }

    private void onNextClick(View v) {
        if (current != total) {
            binding.setLoading(true);
            service.getProductInquiries(product.getId(), current + 1, 3, this);
        }
    }

    private void onPreviousClick(View v) {
        if (current != 0) {
            binding.setLoading(true);
            service.getProductInquiries(product.getId(), current - 1, 3, this);
        }
    }

    @Override
    public void onSuccess(Response mResponse) {
        InquiryListResponse response = (InquiryListResponse) mResponse.body();

        if (response != null) {
            setInquiriesToLayout(response);
        }
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setLoading(false);
        DynamicToast.makeError(activity, mErrorMessage, Toast.LENGTH_SHORT).show();
    }
}
