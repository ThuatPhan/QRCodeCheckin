package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "checkin_histories")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    double longitude;

    @Column(nullable = false)
    boolean success;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    CheckinLocation location;

    @Column(name = "user_id", nullable = false)
    String userId;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime checkinTime;
}
