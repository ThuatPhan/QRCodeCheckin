package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QRCodeRequest {
    @NotNull(message = "LOCATION_REQUIRED")
    Long locationId;
}
