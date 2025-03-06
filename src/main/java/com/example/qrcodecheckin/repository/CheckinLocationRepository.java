package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.CheckinLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinLocationRepository extends JpaRepository<CheckinLocation, Long> {
    boolean existsByNameAndLongitudeAndLatitude(String name, double longitude, double latitude);
}
