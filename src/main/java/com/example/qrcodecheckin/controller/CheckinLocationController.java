package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.CheckinLocationRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.CheckinLocationResponse;
import com.example.qrcodecheckin.dto.response.PagedResponse;
import com.example.qrcodecheckin.service.CheckinLocationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin-locations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CheckinLocationController {
    CheckinLocationService checkinLocationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CheckinLocationResponse> createCheckinLocation(@RequestBody @Valid CheckinLocationRequest checkinLocationRequest) {
        CheckinLocationResponse createdCheckinLocation = checkinLocationService.createCheckinLocation(checkinLocationRequest);
        return ApiResponse.success(createdCheckinLocation, "Check-in location created successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<CheckinLocationResponse> getCheckinLocationById(@PathVariable Long id) {
        return ApiResponse.success(checkinLocationService.findCheckinLocationById(id), "Check-in location fetched successfully");
    }

    @GetMapping
    public ApiResponse<PagedResponse<CheckinLocationResponse>> getAllCheckinLocations(@RequestParam int page, @RequestParam int size) {
        return ApiResponse.success(checkinLocationService.findAllCheckinLocations(page, size), "All check-in locations fetched successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<CheckinLocationResponse> updateCheckinLocation(@PathVariable Long id, @RequestBody @Valid CheckinLocationRequest checkinLocationRequest) {
        return ApiResponse.success(checkinLocationService.updateCheckinLocation(id, checkinLocationRequest), "Check-in location updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCheckinLocation(@PathVariable Long id) {
        checkinLocationService.deleteCheckinLocationById(id);
    }
}
