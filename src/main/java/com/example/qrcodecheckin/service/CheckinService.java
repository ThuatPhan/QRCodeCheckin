package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.CheckinRequest;
import com.example.qrcodecheckin.dto.response.CheckinHistoryResponse;
import com.example.qrcodecheckin.entity.CheckinHistory;
import com.example.qrcodecheckin.entity.CheckinLocation;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.mapper.CheckinHistoryMapper;
import com.example.qrcodecheckin.repository.CheckinHistoryRepository;
import com.example.qrcodecheckin.repository.CheckinLocationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckinService {
    CheckinHistoryRepository checkinHistoryRepository;
    CheckinLocationRepository checkinLocationRepository;
    CheckinHistoryMapper checkinHistoryMapper;
    CheckinQrCodeService checkinQrCodeService;

    @Autowired
    public CheckinService(CheckinHistoryRepository checkinHistoryRepository, CheckinLocationRepository checkinLocationRepository, CheckinHistoryMapper checkinHistoryMapper, CheckinQrCodeService checkinQrCodeService) {
        this.checkinHistoryRepository = checkinHistoryRepository;
        this.checkinLocationRepository = checkinLocationRepository;
        this.checkinHistoryMapper = checkinHistoryMapper;
        this.checkinQrCodeService = checkinQrCodeService;
    }

    public CheckinHistoryResponse checkin(CheckinRequest checkinRequest, String userId) {
        checkinQrCodeService.checkValidQrCode(checkinRequest);
        CheckinLocation checkinLocation = checkinLocationRepository.findById(checkinRequest.getLocationId())
                .orElseThrow(() -> new AppException(ErrorCode.CHECKIN_LOCATION_NOT_EXIST));

        // If user already checked in
        LocalDateTime last24Hours = LocalDateTime.now().minusHours(24);
        boolean hasCheckedInRecently = checkinHistoryRepository.existsByUserIdAndLocationIdAndCheckinTimeGreaterThan(
                userId,
                checkinRequest.getLocationId(),
                last24Hours
        );
        if (hasCheckedInRecently) {
            throw new AppException(ErrorCode.ALREADY_CHECKED_IN_TODAY);
        }

        //Calculate distance between user device and picked location
        double distance = haversineDistance(
                checkinRequest.getLatitude(), checkinRequest.getLongitude(),
                checkinLocation.getLatitude(), checkinLocation.getLongitude()
        );

        boolean isSuccess = distance <= checkinLocation.getRadius();

        CheckinHistory newCheckinHistory = checkinHistoryMapper.toCheckinHistory(checkinRequest);
        newCheckinHistory.setUserId(userId);
        newCheckinHistory.setSuccess(isSuccess);
        newCheckinHistory.setLocation(checkinLocation);

        var result = checkinHistoryRepository.save(newCheckinHistory);

        if(!isSuccess) {
            throw new AppException(ErrorCode.CHECKIN_LOCATION_INVALID);
        }

        return checkinHistoryMapper.toResponse(result);
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }
}
