package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {
}
