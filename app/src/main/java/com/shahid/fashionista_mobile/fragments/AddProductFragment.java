package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.CategorySelectAdapter;
import com.shahid.fashionista_mobile.adapters.FileAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentAddProductBinding;
import com.shahid.fashionista_mobile.dto.request.ProductRequest;
import com.shahid.fashionista_mobile.dto.response.AllTagsResponse;
import com.shahid.fashionista_mobile.dto.response.TagResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

public class AddProductFragment extends AuthFragment {
    private FragmentAddProductBinding binding;

    public static final int PICK_IMAGE_REQUEST = 1;

    RecyclerView brands, genders, types, images;
    FileAdapter adapter;
    EditText name, description, price, stock;
    CategorySelectAdapter brandsAdapter, gendersAdapter, typesAdapter;

    AwesomeValidation validator;

    @Inject
    ProductService service;
    private ServiceCallback categoriesCallback = new ServiceCallback() {
        @Override
        public void onSuccess(Response mResponse) {
            AllTagsResponse response = (AllTagsResponse) mResponse.body();

            if (response == null) {
                return;
            }

            brandsAdapter = new CategorySelectAdapter(response.getBrands());
            gendersAdapter = new CategorySelectAdapter(response.getGenders());
            typesAdapter = new CategorySelectAdapter(response.getTypes());

            brands.setAdapter(brandsAdapter);
            genders.setAdapter(gendersAdapter);
            types.setAdapter(typesAdapter);

            binding.setLoading(false);
        }

        @Override
        public void onFailure(String mErrorMessage) {
            binding.setError(mErrorMessage);
            binding.setLoading(false);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);

        validator = new AwesomeValidation(UNDERLABEL);
        validator.setContext(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        request(activity);

        brands = binding.brands;
        types = binding.types;
        genders = binding.genders;

        name = binding.name;
        description = binding.description;
        price = binding.price;
        stock = binding.stock;
        images = binding.images;

        validator.addValidation(name, RegexTemplate.NOT_EMPTY, "Please enter a product name.");
        validator.addValidation(description, RegexTemplate.NOT_EMPTY, "Please enter a product description.");
        validator.addValidation(price, RegexTemplate.NOT_EMPTY, "Please enter a valid price.");
        validator.addValidation(stock, RegexTemplate.NOT_EMPTY, "Please enter a valid stock.");

        adapter = new FileAdapter(false);
        images.setAdapter(adapter);

        binding.addImageButton.setOnClickListener(this::onAddImagesClick);
        binding.addProductButton.setOnClickListener(this::onAddProductClick);

        binding.setLoading(true);

        service.getProductTags(categoriesCallback);
    }

    private void onAddImagesClick(View view) {
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

    private void onAddProductClick(View view) {
        if (auth == null) {
            return;
        }
        if (!validator.validate()) {
            return;
        }
        TagResponse selectedBrand = brandsAdapter.getTag();

        if (selectedBrand == null) {
            DynamicToast.makeError(activity, "Please select a Brand.").show();
            return;
        }

        TagResponse selectedGender = gendersAdapter.getTag();

        if (selectedGender == null) {
            DynamicToast.makeError(activity, "Please select a Gender.").show();
            return;
        }

        TagResponse selectedType = typesAdapter.getTag();

        if (selectedType == null) {
            DynamicToast.makeError(activity, "Please select a Type.").show();
            return;
        }

        List<File> files = adapter.getFiles();

        if (files.size() < 4) {
            DynamicToast.makeError(activity, "Please select at least four images.").show();
            return;
        }

        String name = this.name.getText().toString();
        String description = this.description.getText().toString();
        String price = (this.price.getText().toString());
        String stock = this.stock.getText().toString();

        File file = files.remove(0);

        RequestBody requestName = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description);
        RequestBody requestPrice = RequestBody.create(MediaType.parse("multipart/form-data"), price);
        RequestBody requestStock = RequestBody.create(MediaType.parse("multipart/form-data"), stock);

        MultipartBody.Part thumbnail = createFormData("thumbnail", file.getName(), create(parse("image/jpeg"), file));
        List<MultipartBody.Part> images = files.stream().map(f -> createFormData("images", f.getName(), create(parse("image/jpeg"), f))).collect(Collectors.toList());

        List<MultipartBody.Part> tags = new ArrayList<>();

        tags.add(createFormData("tags", selectedBrand.getId()));
        tags.add(createFormData("tags", selectedGender.getId()));
        tags.add(createFormData("tags", selectedType.getId()));

        ProductRequest request = new ProductRequest(
                requestName, requestDescription, requestPrice, requestStock, tags, thumbnail, images
        );

        binding.setAdding(true);

        service.createProduct("Bearer " + auth.getToken(), request, new ServiceCallback() {
            @Override
            public void onSuccess(Response mResponse) {
                DynamicToast.makeSuccess(activity, "Product added successfully!").show();
                CustomNavigator.goBack(rootNavController);
            }

            @Override
            public void onFailure(String mErrorMessage) {
                DynamicToast.makeError(activity, mErrorMessage).show();
                binding.setAdding(false);
            }
        });

    }

}
