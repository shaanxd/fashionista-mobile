package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private String id;
    private double totalPrice;
    private int numberOfItems = 0;
    private Date orderedAt;
    private List<CartItemResponse> purchases = new ArrayList<>();
    private String name;
    private String address;
    private String city;
    private String country;
    private String paymentMethod;
}
