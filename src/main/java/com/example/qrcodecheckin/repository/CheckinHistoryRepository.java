package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.CheckinHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CheckinHistoryRepository extends JpaRepository<CheckinHistory, Long> {
    boolean existsByUserIdAndLocationIdAndCheckinTimeGreaterThan(String userId, Long locationId, LocalDateTime timestamp);
}
