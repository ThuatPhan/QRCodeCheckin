package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {
    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Department name must be as least 2 characters")
    String name;
}
