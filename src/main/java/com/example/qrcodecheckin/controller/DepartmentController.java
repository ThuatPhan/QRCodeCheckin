package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.DepartmentRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.DepartmentResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.qrcodecheckin.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DepartmentController {
    DepartmentService departmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DepartmentResponse> createDepartment(@RequestBody @Valid DepartmentRequest departmentRequest) {
        DepartmentResponse createdDepartment = departmentService.createDepartment(departmentRequest);
        return ApiResponse.success(createdDepartment, "Department created successfully");
    }

    @GetMapping
    public ApiResponse<List<DepartmentResponse>> findAll() {
        return ApiResponse.success(departmentService.findAll(), "Departments retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<DepartmentResponse> findById(@PathVariable Long id) {
        return ApiResponse.success(departmentService.findDepartmentById(id), "Department retrieved successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<DepartmentResponse> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentRequest departmentRequest) {
        return ApiResponse.success(departmentService.updateDepartment(id, departmentRequest), "Department updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
