package com.shahid.fashionista_mobile.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListResponse {
    private int total;
    private int current;
    private List<ReviewResponse> reviews;
}
