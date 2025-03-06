package com.example.qrcodecheckin.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse implements Serializable {
    Long id;
    String fullName;
    String email;
    DepartmentResponse department;
}
