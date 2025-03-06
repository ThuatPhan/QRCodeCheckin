package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @Size(min = 5, message = "EMPLOYEE_NAME_INVALID")
    String fullName;
    @Email(message = "EMPLOYEE_EMAIL_INVALID")
    String email;
    Long departmentId;
}
