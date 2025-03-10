package com.example.qrcodecheckin.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentRequest {
    @NotNull(message = "Employee id is required")
    Long employeeId;

    @NotNull(message = "Shift id is required")
    Long shiftId;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be today or in the future")
    LocalDate date;
}
