package com.example.qrcodecheckin.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinRequest {
    Long qrCodeId;
    String qrCodeNonce;
    Long locationId;
    double longitude;
    double latitude;
}
