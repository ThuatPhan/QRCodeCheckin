package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.Location;
import com.example.qrcodecheckin.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRCodeRepository extends JpaRepository<QrCode, Long> {
    Optional<QrCode> findByNonce(String nonce);

    Optional<QrCode> findByLocation(Location location);
}
