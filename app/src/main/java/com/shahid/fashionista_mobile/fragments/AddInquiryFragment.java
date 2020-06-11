package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentAddInquiryBinding;
import com.shahid.fashionista_mobile.dto.request.InquiryRequest;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class AddInquiryFragment extends AuthFragment implements ServiceCallback {
    FragmentAddInquiryBinding binding;
    ProductResponse product;

    AwesomeValidation validator;

    EditText title, description;

    MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    @Inject
    ProductService service;

    public AddInquiryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String json = bundle.getString("PRODUCT_DETAILS", "");
            product = new Gson().fromJson(json, ProductResponse.class);
        }
        validator = new AwesomeValidation(UNDERLABEL);
        validator.setContext(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddInquiryBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = binding.title;
        description = binding.description;

        binding.setProduct(product);
        Glide.with(binding.getRoot())
                .load(product.getThumbnail())
                .placeholder(R.drawable.placeholder_img)
                .into(binding.thumbnailImage);

        validator.addValidation(title, RegexTemplate.NOT_EMPTY, getString(R.string.error_title));
        validator.addValidation(description, RegexTemplate.NOT_EMPTY, getString(R.string.error_description));

        binding.addInquiryButton.setOnClickListener(this::onAddClick);

        loading.observe(getViewLifecycleOwner(), this::onLoadingChange);
    }

    private void onLoadingChange(Boolean loading) {
        binding.setLoading(loading);
    }

    private void onAddClick(View view) {
        if (!validator.validate()) {
            return;
        }
        if (auth == null) {
            return;
        }

        String title = this.title.getText().toString();
        String description = this.description.getText().toString();

        loading.setValue(true);
        InquiryRequest request = new InquiryRequest(title, description);
        service.addInquiry(product.getId(), "Bearer " + auth.getToken(), request, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        CustomNavigator.goBack(rootNavController);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        DynamicToast.makeError(activity, mErrorMessage, Toast.LENGTH_SHORT).show();
        loading.setValue(false);
    }
}
