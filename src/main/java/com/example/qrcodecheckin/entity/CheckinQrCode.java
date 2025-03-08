package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "checkin_qr_codes")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinQrCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String nonce;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    CheckinLocation location;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime expiredAt;

    @Column(nullable = false)
    boolean isExpired = false;
}
