package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private int stock = 0;
    private double price = 0.0;
    private String thumbnail;
    private double avgRating = 0.0;
    private List<String> images = new ArrayList<>();
    private List<TagResponse> tags = new ArrayList<>();
    private ReviewListResponse reviews;
    private InquiryListResponse inquiries;
    private Integer oneStars = null;
    private Integer twoStars = null;
    private Integer threeStars = null;
    private Integer fourStars = null;
    private Integer fiveStars = null;
    private Integer totalStars = null;
}
