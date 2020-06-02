package com.shahid.fashionista_mobile.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String id;
    private String title;
    private String description;
    private double rating = 0.0;
    private Date reviewDate;
    private OwnerResponse owner;
}
