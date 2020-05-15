package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseListResponse {
    private int total = 0;
    private int current = 0;
    private List<PurchaseResponse> purchases = new ArrayList<>();
}
