package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "checkin_locations")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckinLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    double longitude;

    @Positive
    @Column(nullable = false)
    double radius;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    List<CheckinQrCode> qrCodes;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    List<CheckinHistory> history;
}
