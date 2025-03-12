package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendanceRequest {
    @NotNull(message =  "Qr code id is required")
    Long qrCodeId;

    @NotNull(message = "Nonce is required")
    String nonce;
}
