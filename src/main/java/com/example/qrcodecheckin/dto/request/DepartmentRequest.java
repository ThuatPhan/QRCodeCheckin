package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {
    @Size(min = 2, message = "DEPARTMENT_NAME_INVALID")
    String name;
}
