package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.AttendanceRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.AttendanceResponse;
import com.example.qrcodecheckin.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AttendanceController {
    AttendanceService attendanceService;

    @PostMapping
    public ApiResponse<AttendanceResponse> attendance(@RequestBody @Valid AttendanceRequest attendanceRequest) {
        return ApiResponse.success(attendanceService.attendance(attendanceRequest), null);
    }
}
