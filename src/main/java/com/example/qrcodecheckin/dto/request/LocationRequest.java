package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationRequest {
    @Length(min = 5, message = "LOCATION_NAME_INVALID")
    String name;

    @NotNull(message = "LATITUDE_INVALID")
    @Min(-90)
    @Max(90)
    Double latitude;

    @NotNull(message = "LONGITUDE_INVALID")
    @Min(-180)
    @Max(180)
    Double longitude;
}

