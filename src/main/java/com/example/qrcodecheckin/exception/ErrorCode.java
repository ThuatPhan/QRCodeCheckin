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
    //Request body
    INVALID_REQUEST(1000, "Invalid request body"),
    //Department errors
    DEPARTMENT_NAME_INVALID(400, "Department name must be as least 2 characters"),
    DEPARTMENT_NOT_EXIST(404, "Department not exist"),
    DEPARTMENT_NAME_EXISTED(409, "Department name existed"),
    //Employee errors
    EMPLOYEE_NOT_EXIST(404, "Employee not exist"),
    EMPLOYEE_NAME_INVALID(400, "Employee name must be as least 5 characters"),
    EMPLOYEE_EMAIL_EXISTED(409, "Email is existed"),
    EMPLOYEE_EMAIL_INVALID(400, "Invalid email format"),
    //User errors
    USERNAME_INVALID(400, "Username must be as least 5 characters"),
    PASSWORD_INVALID(400, "Password must be as least 5 characters"),
    FIRSTNAME_INVALID(400, "Firstname must be as least 3 characters"),
    LASTNAME_INVALID(400, "Lastname must be as least 3 characters"),
    USERNAME_EXISTED(409, "Username existed"),
    USERNAME_NOT_EXIST(404, "Username not exist"),
    //Authentication errors
    UNAUTHENTICATED(401, "Unauthenticated"),
    ROLE_NOT_EXIST(404, "Role not exist"),
    GENERATE_TOKEN_FAILED(500, "Failed when generating token"),
    //Location errors
    LOCATION_NAME_INVALID(400, "Name must be as least 5 characters"),
    LATITUDE_INVALID(400, "Latitude must be in range -90 to 90"),
    LONGITUDE_INVALID(400, "Longitude must be in range -180 to 180"),
    LOCATION_NOT_EXIST(404, "Location not exist"),
    LOCATION_EXISTED(409, "Location already existed"),
    //QR Code errors
    LOCATION_REQUIRED(400, "Location id is required")
    ;
    int code;
    String message;
}
