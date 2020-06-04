package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.FileAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentAddCategoryBinding;
import com.shahid.fashionista_mobile.dto.request.CategoryRequest;
import com.shahid.fashionista_mobile.services.ProductService;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;
import static com.shahid.fashionista_mobile.DocumentHelper.getPath;
import static okhttp3.MediaType.parse;
import static okhttp3.MultipartBody.Part.createFormData;
import static okhttp3.RequestBody.create;


public class AddCategoryFragment extends AuthFragment implements ServiceCallback {
    private FragmentAddCategoryBinding binding;

    public static final int PICK_IMAGE_REQUEST = 1;

    EditText name, description;
    RadioGroup typeGroup;

    AwesomeValidation validator;
    @Inject
    ProductService service;
    private FileAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);

        validator = new AwesomeValidation(UNDERLABEL);
        validator.setContext(activity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCategoryBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        request(activity);

        binding.addImageButton.setOnClickListener(this::onAddImageClick);
        binding.addCategoryButton.setOnClickListener(this::onAddCategoryClick);

        name = binding.name;
        description = binding.description;
        typeGroup = binding.typeGroup;

        validator.addValidation(name, RegexTemplate.NOT_EMPTY, "Please enter a name.");
        validator.addValidation(description, RegexTemplate.NOT_EMPTY, "Please enter a description.");

        adapter = new FileAdapter(true);

        binding.images.setAdapter(adapter);
    }

    private void onAddCategoryClick(View view) {
        if (!validator.validate()) {
            return;
        }
        if (authState == null) {
            return;
        }
        if (adapter.getItemCount() == 0) {
            DynamicToast.makeError(activity, "Please select an image.").show();
            return;
        }
        String typeString = "";

        switch (typeGroup.getCheckedRadioButtonId()) {
            case R.id.typeBrand: {
                typeString = "TAG_BRAND";
                break;
            }
            case R.id.typeType: {
                typeString = "TAG_TYPE";
                break;
            }
            case R.id.typeGender: {
                typeString = "TAG_GENDER";
                break;
            }
            default: {
                typeString = null;
            }
        }

        if (typeString == null) {
            DynamicToast.makeError(activity, "Please select a type.").show();
            return;
        }

        String nameString = this.name.getText().toString();
        String descriptionString = this.description.getText().toString();

        File file = adapter.getFiles().get(0);

        RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), nameString);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
        RequestBody requestType = RequestBody.create(MediaType.parse("multipart/form-data"), typeString);
        MultipartBody.Part image = createFormData("image", file.getName(), create(parse("image/jpg"), file));

        CategoryRequest request = new CategoryRequest(
                requestName,
                requestDescription,
                requestType,
                image
        );

        binding.setLoading(true);

        service.createCategory("Bearer " + authState.getToken(), request, this);
    }

    private void onAddImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    public void request(Activity activity) {
        if (checkSelfPermission(activity, STORAGE) != PERMISSION_GRANTED) {
            String[] list = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(activity, list, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            onFileSelect(data.getData());
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onFileSelect(Uri uri) {
        String path = getPath(activity, uri);
        if (path == null || path.isEmpty()) {
            DynamicToast.makeError(activity, "Error obtaining file from device. Please try again.").show();
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            DynamicToast.makeError(activity, "Error obtaining permission to retrieve from device. Please try again.").show();
            return;
        }
        adapter.registerFile(file);
    }

    @Override
    public void onSuccess(Response mResponse) {
        DynamicToast.makeSuccess(activity, "Category added successfully!").show();
        CustomNavigator.goBack(rootNavController);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        DynamicToast.makeError(activity, mErrorMessage).show();
        binding.setLoading(false);
    }
}
