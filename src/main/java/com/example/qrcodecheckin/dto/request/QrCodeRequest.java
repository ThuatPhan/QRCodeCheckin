package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QrCodeRequest {
    @NotNull(message = "Location id is required")
    Long locationId;
}
