package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.InquiryAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentInquiriesBinding;
import com.shahid.fashionista_mobile.dto.response.InquiryListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class InquiriesFragment extends AuthFragment implements ServiceCallback {
    FragmentInquiriesBinding binding;

    RecyclerView inquiries;

    InquiryAdapter adapter;

    int current = 0;
    int total = 0;
    int size = 0;

    @Inject
    ProductService service;

    public InquiriesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInquiriesBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setLoading(true);

        inquiries = binding.inquiries;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);
    }

    private void onNextClick(View v) {
        if (current != total) {
            getAllInquiries(current + 1);
        }
    }

    private void onPreviousClick(View v) {
        if (current != 0) {
            getAllInquiries(current - 1);
        }
    }

    private void getAllInquiries(int page) {
        if (auth == null) {
            return;
        }
        binding.setLoading(true);
        service.getAllInquiries("Bearer " + auth.getToken(), page, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.setLoading(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllInquiries(current);
    }

    @Override
    public void onSuccess(Response mResponse) {
        InquiryListResponse page = (InquiryListResponse) mResponse.body();

        if (page == null) {
            return;
        }

        current = page.getCurrent();
        total = page.getTotal();
        size = page.getInquiries().size();

        binding.setCurrent(current);
        binding.setTotal(total);
        binding.setSize(size);

        if (adapter == null) {
            adapter = new InquiryAdapter(page.getInquiries(), this::onItemClick);
        } else {
            adapter.setInquiries(page.getInquiries());
        }
        inquiries.setAdapter(adapter);

        binding.setLoading(false);
    }

    private void onItemClick(String inquiry) {
        Bundle bundle = new Bundle();
        bundle.putString("INQUIRY_DETAILS", inquiry);
        CustomNavigator.navigate(rootNavController, R.id.action_adminNavigationFragment_to_singleInquiryFragment, bundle);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setError(mErrorMessage);
        binding.setLoading(false);
    }
}
