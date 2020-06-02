package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListResponse {
    private int total = 0;
    private int current = 0;
    private List<InquiryResponse> inquiries = new ArrayList<>();
}
