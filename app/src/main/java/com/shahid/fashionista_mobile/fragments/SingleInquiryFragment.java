package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.ReplyAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentSingleInquiryBinding;
import com.shahid.fashionista_mobile.dto.request.ReplyRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.dto.response.InquiryResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class SingleInquiryFragment extends RootFragment implements ServiceCallback {
    FragmentSingleInquiryBinding binding;

    InquiryResponse inquiry;
    ReplyAdapter adapter;
    EditText reply;
    RecyclerView replies;

    @Nullable
    @Inject
    AuthenticationResponse auth;
    @Inject
    ProductService service;

    AwesomeValidation validator;

    public SingleInquiryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);

        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        }
        String inquiryJSON = bundle.getString("INQUIRY_DETAILS", "");
        inquiry = new Gson().fromJson(inquiryJSON, InquiryResponse.class);

        validator = new AwesomeValidation(UNDERLABEL);
        validator.setContext(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSingleInquiryBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLoading(false);

        reply = binding.reply;
        replies = binding.replies;

        binding.setAuth(auth);
        binding.add.setOnClickListener(this::onAddReply);

        validator.addValidation(reply, RegexTemplate.NOT_EMPTY, "Please enter a reply");
        setInquiry(inquiry);
    }

    private void setInquiry(InquiryResponse inquiry) {
        binding.setInquiry(inquiry);

        Glide.with(binding.getRoot())
                .load(inquiry.getProduct().getThumbnail())
                .placeholder(R.drawable.placeholder_img)
                .into(binding.thumbnail);

        if (adapter == null) {
            adapter = new ReplyAdapter(inquiry.getReplies());
        } else {
            adapter.setReplies(inquiry.getReplies());
        }
        replies.setAdapter(adapter);
    }

    private void onAddReply(View view) {
        if (auth == null) {
            return;
        }
        if (!validator.validate()) {
            return;
        }
        binding.setLoading(true);
        service.addReply(inquiry.getId(), "Bearer " + auth.getToken(), new ReplyRequest(reply.getText().toString()), this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        DynamicToast.makeSuccess(activity, "Reply added successfully!").show();
        InquiryResponse inquiry = (InquiryResponse) mResponse.body();
        if (inquiry == null) {
            return;
        }
        setInquiry(inquiry);
        binding.reply.setText("");
        binding.setLoading(false);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        DynamicToast.makeError(activity, mErrorMessage).show();
        binding.setLoading(false);
    }
}
