package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "checkin_histories")
@Data
public class CheckinHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private boolean success;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private CheckinLocation location;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime checkinTime;
}
