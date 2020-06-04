package com.shahid.fashionista_mobile.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllTagsResponse {
    private List<TagResponse> brands = new ArrayList<>();
    private List<TagResponse> genders = new ArrayList<>();
    private List<TagResponse> types = new ArrayList<>();
}
