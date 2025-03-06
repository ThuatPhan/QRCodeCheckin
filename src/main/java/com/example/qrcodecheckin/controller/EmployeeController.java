package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.EmployeeRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.EmployeeResponse;
import com.example.qrcodecheckin.dto.response.PagedResponse;
import com.example.qrcodecheckin.service.Auth0Service;
import com.example.qrcodecheckin.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, Auth0Service auth0Service) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        EmployeeResponse createdEmployee = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.created(URI.create("/api/employees/" + createdEmployee.getId())).body(new ApiResponse<>(201, null, createdEmployee));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<EmployeeResponse>>> getEmployees(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, employeeService.findAll(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, employeeService.findEmployeeById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, employeeService.updateEmployee(id, employeeRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
