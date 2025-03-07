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
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CheckinController {
    CheckinQrCodeService checkinQrCodeService;
    CheckinService checkinService;

    @PostMapping("/qrcode/generate")
    public ApiResponse<CheckinQrCodeResponse> generateQrCodeInfo(@RequestBody @Valid CheckinQrCodeRequest checkinQrCodeRequest) {
        return ApiResponse.success(checkinQrCodeService.generateQrCode(checkinQrCodeRequest), "QR Code generated successfully");
    }

    @PutMapping("/qrcode/set-expired")
    public ApiResponse<CheckinQrCodeResponse> setExpiredQrCode(@RequestParam Long qrCodeId) {
        return ApiResponse.success(checkinQrCodeService.setExpiredQrCode(qrCodeId), "QR Code expired successfully");
    }

    @PostMapping
    public ApiResponse<CheckinHistoryResponse> checkin(@RequestBody @Valid CheckinRequest checkinRequest) {
        return ApiResponse.success(checkinService.checkin(checkinRequest, ""), "Check-in successful");
    }
}
