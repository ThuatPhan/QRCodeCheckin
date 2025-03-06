package com.example.qrcodecheckin.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinLocationResponse implements Serializable {
    Long id;
    String name;
    double latitude;
    double longitude;
    double radius;
}
