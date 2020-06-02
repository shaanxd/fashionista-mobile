package com.shahid.fashionista_mobile.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyResponse {
    private String id;
    private String reply;
    private OwnerResponse owner;
    private Date date;
}
