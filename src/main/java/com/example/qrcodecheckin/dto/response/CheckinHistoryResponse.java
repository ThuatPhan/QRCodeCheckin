package com.example.qrcodecheckin.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinHistoryResponse {
    double latitude;
    double longitude;
    boolean success;
    String checkinTime;
}
