package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.ReviewAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentRatingBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class RatingFragment extends Fragment implements ServiceCallback {

    FragmentRatingBinding binding;
    ProductResponse product;
    RecyclerView reviewsList;
    ReviewAdapter adapter;

    int current = 0;
    int total = 0;

    Activity activity;

    @Inject
    ProductService service;

    public RatingFragment(ProductResponse product) {
        this.product = product;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsList = binding.reviewsList;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);

        //Set Review data
        binding.setProduct(product);
        setReviewsToLayout(product.getReviews());
    }

    private void setReviewsToLayout(ReviewListResponse page) {
        current = page.getCurrent();
        total = page.getTotal();

        binding.setCurrent(current);
        binding.setTotal(total);

        if (adapter == null) {
            adapter = new ReviewAdapter(page.getReviews());
            reviewsList.setAdapter(adapter);
        } else {
            adapter.setReviews(page.getReviews());
        }
    }

    private void onNextClick(View v) {
        if (current != total) {
            service.getProductReviews(product.getId(), current + 1, 3, this);
        }
    }

    private void onPreviousClick(View v) {
        if (current != 0) {
            service.getProductReviews(product.getId(), current - 1, 3, this);
        }
    }

    @Override
    public void onSuccess(Response mResponse) {
        ReviewListResponse response = (ReviewListResponse) mResponse.body();
        if (response != null) {
            setReviewsToLayout(response);
        }
    }

    @Override
    public void onFailure(String mErrorMessage) {
        System.out.println(mErrorMessage);
    }
}