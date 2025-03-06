package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "checkin_locations")
@Data
public class CheckinLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Positive
    @Column(nullable = false)
    private double radius;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<CheckinQrCode> qrCodes;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<CheckinHistory> history;
}
