package com.example.qrcodecheckin.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinLocationRequest {
    @Size(min = 3, message = "LOCATION_NAME_INVALID")
    String name;
    @JsonProperty(required = true)
    double latitude;
    @JsonProperty(required = true)
    double longitude;
    @JsonProperty(required = true)
    @Positive(message = "RADIUS_INVALID")
    double radius;
}
