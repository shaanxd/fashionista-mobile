package com.shahid.fashionista_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    private String id;
    private String name;
    private String type;
    private String image;
}
