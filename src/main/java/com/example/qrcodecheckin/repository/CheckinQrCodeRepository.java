package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.CheckinQrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CheckinQrCodeRepository extends JpaRepository<CheckinQrCode, Long> {
    Optional<CheckinQrCode> findFirstByLocationIdAndCreatedAtAfterOrderByCreatedAtDesc(Long locationId, LocalDateTime createdAt);
    Optional<CheckinQrCode> findByIdAndNonce(Long qrCodeId, String nonce);
}
