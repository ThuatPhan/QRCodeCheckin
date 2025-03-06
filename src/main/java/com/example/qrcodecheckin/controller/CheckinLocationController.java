package com.example.qrcodecheckin.controller;

import com.example.qrcodecheckin.dto.request.CheckinLocationRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;
import com.example.qrcodecheckin.dto.response.CheckinLocationResponse;
import com.example.qrcodecheckin.dto.response.PagedResponse;
import com.example.qrcodecheckin.service.CheckinLocationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/checkin-locations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckinLocationController {
    CheckinLocationService checkinLocationService;

    @Autowired
    public CheckinLocationController(CheckinLocationService checkinLocationService) {
        this.checkinLocationService = checkinLocationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CheckinLocationResponse>> createCheckinLocation(@RequestBody @Valid CheckinLocationRequest checkinLocationRequest) {
        CheckinLocationResponse createdCheckinLocation = checkinLocationService.createCheckinLocation(checkinLocationRequest);
        return ResponseEntity.created(URI.create("/api/checkin-locations/" + createdCheckinLocation.getId())).body(new ApiResponse<>(201, null, createdCheckinLocation));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CheckinLocationResponse>> getCheckinLocationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinLocationService.findCheckinLocationById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CheckinLocationResponse>>> getAllCheckinLocations(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinLocationService.findAllCheckinLocations(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CheckinLocationResponse>> updateCheckinLocation(@PathVariable Long id, @RequestBody @Valid CheckinLocationRequest checkinLocationRequest) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinLocationService.updateCheckinLocation(id, checkinLocationRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCheckinLocation(@PathVariable Long id) {
        checkinLocationService.deleteCheckinLocationById(id);
        return ResponseEntity.noContent().build();
    }
}
