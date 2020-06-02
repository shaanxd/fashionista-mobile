package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponse {
    private String id;
    private String title;
    private String description;
    private OwnerResponse owner;
    private Date inquiryDate;
    private ProductResponse product;
    private List<ReplyResponse> replies = new ArrayList<>();
}
