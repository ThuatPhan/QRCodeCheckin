package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.AssignmentRequest;
import com.example.qrcodecheckin.dto.response.AssignmentResponse;
import com.example.qrcodecheckin.entity.Assignment;
import com.example.qrcodecheckin.enums.EmploymentType;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.mapper.AssignmentMapper;
import com.example.qrcodecheckin.repository.AssignmentRepository;
import com.example.qrcodecheckin.repository.EmployeeRepository;
import com.example.qrcodecheckin.repository.ShiftRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssignmentService {
    EmployeeRepository employeeRepository;
    ShiftRepository shiftRepository;
    AssignmentRepository assignmentRepository;
    AssignmentMapper assignmentMapper;


    public AssignmentResponse createAssignment(AssignmentRequest assignmentRequest) {
        var employee = employeeRepository
                .findById(assignmentRequest.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));

        if (employee.getEmploymentType().equals(EmploymentType.FULL_TIME)) {
            throw new AppException(ErrorCode.INVALID_EMPLOYMENT_TYPE);
        }

        var shift = shiftRepository.findById(assignmentRequest.getShiftId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIFT_NOT_EXIST));

        LocalDate today = LocalDate.now();
        LocalDate requestDate = assignmentRequest.getDate();

        if (requestDate.isEqual(today) && shift.getEndTime().isBefore(LocalTime.now())) {
            throw new AppException(ErrorCode.INVALID_ASSIGNMENT_TIME);
        }

        if (assignmentRequest.getDate().isEqual(LocalDate.now()) &&
                assignmentRepository.existsByEmployeeAndDateAndShift(employee, assignmentRequest.getDate(), shift)) {
            throw new AppException(ErrorCode.EMPLOYEE_ALREADY_ASSIGNMENT);
        }

        var newAssignment = Assignment
                .builder()
                .employee(employee)
                .shift(shift)
                .date(assignmentRequest.getDate())
                .build();

        return assignmentMapper.toResponse(assignmentRepository.save(newAssignment));
    }

}
