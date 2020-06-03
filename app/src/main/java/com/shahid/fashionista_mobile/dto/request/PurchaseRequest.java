package com.shahid.fashionista_mobile.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    private List<String> cart;
    private String name;
    private String address;
    private String city;
    private String country;
    private String paymentMethod;
}
