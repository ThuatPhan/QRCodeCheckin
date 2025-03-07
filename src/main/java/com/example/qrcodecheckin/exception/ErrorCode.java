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
    CHECKIN_LOCATION_INVALID(5005, "Check-in location invalid"),
    
    CHECKIN_QRCODE_INVALID(6001, "Check-in qrcode invalid"),
    CHECKIN_QRCODE_EXPIRED(6002, "Qr code is expired"),
    CHECKIN_QRCODE_NOT_EXIST(6003, "Qr code not exist"),
    ALREADY_CHECKED_IN_TODAY(6004, "Already checked In Today"),

    USERNAME_INVALID(7001, "Username must be as least 5 characters"),
    PASSWORD_INVALID(7001, "Password must be as least 5 characters"),
    FIRSTNAME_INVALID(7002, "Firstname must be as least 3 characters"),
    LASTNAME_INVALID(7003, "Lastname must be as least 3 characters"),
    USERNAME_EXISTED(7004, "Username existed"),
    USERNAME_NOT_EXIST(7005, "Username not exist"),

    UNAUTHENTICATED(8000, "Unauthenticated");

    int code;
    String message;
}
