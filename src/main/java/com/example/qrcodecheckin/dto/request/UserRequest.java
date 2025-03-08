package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequest {
    @Size(min = 3, message = "FIRSTNAME_INVALID")
    String firstName;

    @Size(min = 3, message = "LASTNAME_INVALID")
    String lastName;

    @Size(min = 5, message = "USERNAME_INVALID")
    String username;

    @Size(min = 5, message = "PASSWORD_INVALID")
    String password;

    Long employeeId;
}
