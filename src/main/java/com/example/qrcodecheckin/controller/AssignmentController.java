package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.AssignmentRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.AssignmentResponse;
import com.example.qrcodecheckin.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ApiResponse<AssignmentResponse> createAssignment(@RequestBody @Valid AssignmentRequest assignmentRequest) {
        return ApiResponse.success(assignmentService.createAssignment(assignmentRequest), null);
    }
}
