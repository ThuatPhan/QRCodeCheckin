package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.DepartmentRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.DepartmentResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.qrcodecheckin.service.DepartmentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DepartmentController {
    DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentResponse>> createDepartment(@RequestBody DepartmentRequest departmentRequest) {
        DepartmentResponse createdDepartment = departmentService.createDepartment(departmentRequest);
        return ResponseEntity.created(URI.create("/api/employees/" + createdDepartment.getId())).body(new ApiResponse<>(201, null, createdDepartment));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> findAll() {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, departmentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, departmentService.findDepartmentById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentResponse>> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentRequest departmentRequest) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, departmentService.updateDepartment(id, departmentRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
