package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {
    private int total;
    private int current;
    private List<ProductResponse> products = new ArrayList<>();
}
