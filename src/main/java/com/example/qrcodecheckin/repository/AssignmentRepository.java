package com.example.qrcodecheckin.repository;

import com.example.qrcodecheckin.entity.Assignment;
import com.example.qrcodecheckin.entity.Employee;
import com.example.qrcodecheckin.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    boolean existsByEmployeeAndDateAndShift(Employee employee, LocalDate date, Shift shift);

    List<Assignment> findByEmployeeAndDate(Employee employee, LocalDate date);
}

