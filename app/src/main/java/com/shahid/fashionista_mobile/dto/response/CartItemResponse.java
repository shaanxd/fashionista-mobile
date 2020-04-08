package com.shahid.fashionista_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private String id;
    private int quantity;
    private double totalPrice;
    private String size;
    private ProductResponse product;
}
