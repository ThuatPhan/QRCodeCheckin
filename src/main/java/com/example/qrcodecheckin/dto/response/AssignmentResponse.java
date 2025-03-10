package com.example.qrcodecheckin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {
    Long id;
    ShiftResponse shift;
    EmployeeResponse employee;
    LocalDate date;
}
