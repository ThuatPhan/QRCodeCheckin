package com.example.qrcodecheckin.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinQrCodeResponse {
    Long id;
    String nonce;
    CheckinLocationResponse location;
    String createdAt;
    String expiredAt;
}
