package com.example.qrcodecheckin.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_ERROR(999, "Uncategorized error"),
    INVALID_REQUEST(1000, "Invalid request body"),

    DEPARTMENT_NOT_EXIST(2000, "Department not exist"),
    DEPARTMENT_NAME_EXISTED(2001, "Department name existed"),
    DEPARTMENT_NAME_INVALID(2002, "Department name must be as least 2 characters"),

    EMPLOYEE_NOT_EXIST(3000, "Employee not exist"),
    EMPLOYEE_NAME_INVALID(3001, "Employee name must be as least 5 characters"),
    EMPLOYEE_EMAIL_EXISTED(3002, "Email is existed"),
    EMPLOYEE_EMAIL_INVALID(3003, "Invalid email format"),

    FAILED_TO_GET_TOKEN(4000, "Failed when retrieving access token from Auth0"),
    FAILED_TO_CREATE_ACCOUNT(4001, "Failed when creating user account"),

    LOCATION_NAME_INVALID(5000, "Location name must be as least 5 characters"),
    LOCATION_ID_INVALID(5001, "Location id must be an positive integer"),
    RADIUS_INVALID(5002, "Radius must be positive number"),
    CHECKIN_LOCATION_NOT_EXIST(5003, "Check-in location not exist"),
    CHECKIN_LOCATION_EXISTED(5004, "Check-in location existed"),
    CHECKIN_LOCATION_INVALID(5005, "Vị trí điểm danh không hợp lệ"),
    
    CHECKIN_QRCODE_INVALID(6001, "Mã Qr không hợp lệ"),
    CHECKIN_QRCODE_EXPIRED(6002, "Mã Qr đã hết hạn"),
    CHECKIN_QRCODE_NOT_EXIST(6003, "Mã Qr không tồn tại"),
    ALREADY_CHECKED_IN_TODAY(6004, "Người dùng đã điểm danh trong ngày"),;

    int code;
    String message;
}
