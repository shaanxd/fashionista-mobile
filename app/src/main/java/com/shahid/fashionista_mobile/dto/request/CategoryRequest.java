package com.shahid.fashionista_mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {
    private RequestBody name;
    private RequestBody description;
    private RequestBody type;
    private MultipartBody.Part image;
}
