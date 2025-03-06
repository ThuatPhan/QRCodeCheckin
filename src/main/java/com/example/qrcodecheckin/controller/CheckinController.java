package com.example.qrcodecheckin.controller;


import com.example.qrcodecheckin.dto.request.CheckinQrCodeRequest;
import com.example.qrcodecheckin.dto.request.CheckinRequest;
import com.example.qrcodecheckin.dto.response.ApiResponse;

import com.example.qrcodecheckin.dto.response.CheckinHistoryResponse;
import com.example.qrcodecheckin.dto.response.CheckinQrCodeResponse;
import com.example.qrcodecheckin.service.CheckinQrCodeService;
import com.example.qrcodecheckin.service.CheckinService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckinController {
    CheckinQrCodeService checkinQrCodeService;
    CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinQrCodeService checkinQrCodeService, CheckinService checkinService) {
        this.checkinQrCodeService = checkinQrCodeService;
        this.checkinService = checkinService;
    }

    @PostMapping("/qrcode/generate")
    public ResponseEntity<ApiResponse<CheckinQrCodeResponse>> generateQrCodeInfo(@RequestBody @Valid CheckinQrCodeRequest checkinQrCodeRequest) throws InterruptedException {
        Thread.sleep(1000);
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinQrCodeService.generateQrCode(checkinQrCodeRequest)));
    }

    @PutMapping("/qrcode/set-expired")
    public ResponseEntity<ApiResponse<CheckinQrCodeResponse>> setExpiredQrCode(@RequestParam Long qrCodeId) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinQrCodeService.setExpiredQrCode(qrCodeId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CheckinHistoryResponse>> checkin(@RequestBody @Valid CheckinRequest checkinRequest, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok().body(new ApiResponse<>(200, null, checkinService.checkin(checkinRequest, jwt.getSubject())));
    }
}
