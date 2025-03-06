package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinQrCodeRequest {
    @Positive(message = "LOCATION_ID_INVALID")
    Long locationId;
}
