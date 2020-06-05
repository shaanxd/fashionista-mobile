package com.shahid.fashionista_mobile.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private RequestBody name;
    private RequestBody description;
    private RequestBody price;
    private RequestBody stock;
    private List<MultipartBody.Part> tags;
    private MultipartBody.Part thumbnail;
    private List<MultipartBody.Part> images;
}
