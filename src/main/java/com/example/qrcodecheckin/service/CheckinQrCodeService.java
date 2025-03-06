package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.CheckinQrCodeRequest;
import com.example.qrcodecheckin.dto.request.CheckinRequest;
import com.example.qrcodecheckin.dto.response.CheckinQrCodeResponse;
import com.example.qrcodecheckin.entity.CheckinLocation;
import com.example.qrcodecheckin.entity.CheckinQrCode;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.mapper.CheckinQrCodeMapper;
import com.example.qrcodecheckin.repository.CheckinLocationRepository;
import com.example.qrcodecheckin.repository.CheckinQrCodeRepository;
import com.example.qrcodecheckin.util.NonceGenerator;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckinQrCodeService {
    CheckinLocationRepository checkinLocationRepository;
    CheckinQrCodeRepository checkinQrCodeRepository;
    CheckinQrCodeMapper checkinQrCodeMapper;

    @Autowired
    public CheckinQrCodeService(CheckinLocationRepository checkinLocationRepository, CheckinQrCodeRepository checkinQrCodeRepository, CheckinQrCodeMapper checkinQrCodeMapper) {
        this.checkinLocationRepository = checkinLocationRepository;
        this.checkinQrCodeRepository = checkinQrCodeRepository;
        this.checkinQrCodeMapper = checkinQrCodeMapper;
    }

    public CheckinQrCodeResponse generateQrCode(CheckinQrCodeRequest checkinQrCodeRequest) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryThreshold = now.minusMinutes(2);
        Long locationId = checkinQrCodeRequest.getLocationId();
        //Check expiry of qr code
        Optional<CheckinQrCode> existingQrCode = checkinQrCodeRepository.findFirstByLocationIdAndCreatedAtAfterOrderByCreatedAtDesc(locationId, expiryThreshold);
        if (existingQrCode.isPresent() && !existingQrCode.get().isExpired()) {
            return checkinQrCodeMapper.toResponse(existingQrCode.get());
        }
        //Expired -> create new qr code
        CheckinLocation checkinLocation = checkinLocationRepository.findById(locationId).orElseThrow(() -> new AppException(ErrorCode.CHECKIN_LOCATION_NOT_EXIST));
        CheckinQrCode newQrCode = new CheckinQrCode();
        newQrCode.setLocation(checkinLocation);
        newQrCode.setNonce(NonceGenerator.generateNonce());
        newQrCode.setExpiredAt(LocalDateTime.now().plusMinutes(2));

        return checkinQrCodeMapper.toResponse(checkinQrCodeRepository.save(newQrCode));
    }

    public void checkValidQrCode(CheckinRequest checkinRequest) {
        Long qrCodeId = checkinRequest.getQrCodeId();
        String nonce = checkinRequest.getQrCodeNonce();
        LocalDateTime now = LocalDateTime.now();

        CheckinQrCode qrCode = checkinQrCodeRepository.findByIdAndNonce(qrCodeId, nonce)
                .orElseThrow(() -> new AppException(ErrorCode.CHECKIN_QRCODE_INVALID));

        if (qrCode.getExpiredAt().isBefore(now)) {
            throw new AppException(ErrorCode.CHECKIN_QRCODE_EXPIRED);
        }

    }

    public CheckinQrCodeResponse setExpiredQrCode(Long qrCodeId) {
        CheckinQrCode qrCode = checkinQrCodeRepository.findById(qrCodeId).orElseThrow(() -> new AppException(ErrorCode.CHECKIN_QRCODE_NOT_EXIST));
        qrCode.setExpired(true);
        return checkinQrCodeMapper.toResponse(checkinQrCodeRepository.save(qrCode));
    }
}
