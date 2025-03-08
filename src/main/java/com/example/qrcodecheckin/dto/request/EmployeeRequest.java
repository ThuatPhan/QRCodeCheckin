package com.example.qrcodecheckin.dto.request;

import com.example.qrcodecheckin.enums.EmploymentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @Size(min = 5, message = "EMPLOYEE_FIRSTNAME_INVALID")
    String firstName;

    @Size(min = 5, message = "EMPLOYEE_LASTNAME_INVALID")
    String lastName;

    @Past(message = "EMPLOYEE_BIRTHDAY_INVALID")
    LocalDate dateOfBirth;

    @Email(message = "EMPLOYEE_EMAIL_INVALID")
    String email;

    @NotNull
    EmploymentType employmentType;

    Long departmentId;
}
