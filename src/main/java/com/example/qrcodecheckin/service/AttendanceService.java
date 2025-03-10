package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.AttendanceRequest;
import com.example.qrcodecheckin.dto.response.AttendanceResponse;
import com.example.qrcodecheckin.entity.Assignment;
import com.example.qrcodecheckin.entity.AttendanceLog;
import com.example.qrcodecheckin.entity.Employee;
import com.example.qrcodecheckin.entity.User;
import com.example.qrcodecheckin.enums.AttendanceStatus;
import com.example.qrcodecheckin.enums.EmploymentType;
import com.example.qrcodecheckin.enums.ShiftEnum;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.mapper.AttendanceMapper;
import com.example.qrcodecheckin.repository.AssignmentRepository;
import com.example.qrcodecheckin.repository.AttendanceLogRepository;
import com.example.qrcodecheckin.repository.QrCodeRepository;
import com.example.qrcodecheckin.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceService {
    AttendanceLogRepository attendanceLogRepository;
    UserRepository userRepository;
    QrCodeRepository qrCodeRepository;
    AttendanceMapper attendanceMapper;
    AssignmentRepository assignmentRepository;


    public AttendanceResponse attendance(AttendanceRequest request) {
        User user = getUserFromToken();
        Employee employee = user.getEmployee();
        LocalDateTime now = LocalDateTime.now();

        validateQrCode(request.getQrCodeId(), request.getNonce());

        Optional<AttendanceLog> log = attendanceLogRepository
                .findFirstByEmployeeAndCheckinTimeBetweenOrderByCheckinTimeDesc(
                        employee, now.toLocalDate().atStartOfDay(), now.toLocalDate().atTime(23, 59, 59)
                );

        if (log.isEmpty()) {
            return handleCheckIn(employee, now);
        } else if (log.get().getCheckoutTime() == null) {
            return handleCheckOut(log.get(), now);
        } else {
            throw new AppException(ErrorCode.ALREADY_CHECKED_OUT);
        }
    }

    private AttendanceResponse handleCheckIn(Employee employee, LocalDateTime checkinTime) {
        if (employee.getEmploymentType() == EmploymentType.PART_TIME) {
            List<Assignment> assignments = assignmentRepository.findByEmployeeAndDate(employee, checkinTime.toLocalDate());

            if (assignments.isEmpty()) {
                throw new AppException(ErrorCode.NO_SHIFT_ASSIGNED_TODAY);
            }

            // ✅ Chặn check-in trước/sau ngày ca làm
            Assignment assignment = assignments.stream()
                    .filter(a -> a.getDate().isEqual(checkinTime.toLocalDate()))  // Chỉ check-in đúng ngày ca làm
                    .filter(a -> checkinTime.toLocalTime().isAfter(a.getShift().getStartTime().minusMinutes(15)))
                    .min(Comparator.comparing(a -> a.getShift().getStartTime()))
                    .orElseThrow(() -> new AppException(ErrorCode.NO_VALID_SHIFT_FOR_CHECKIN));

            LocalTime shiftStartTime = assignment.getShift().getStartTime();
            AttendanceStatus status = checkinTime.toLocalTime().isAfter(shiftStartTime) ? AttendanceStatus.LATE : AttendanceStatus.VALID;

            AttendanceLog log = AttendanceLog.builder()
                    .employee(employee)
                    .checkinTime(checkinTime)
                    .status(status)
                    .build();

            return attendanceMapper.toResponse(attendanceLogRepository.save(log));
        } else {
            throw new AppException(ErrorCode.INVALID_EMPLOYMENT_TYPE);
        }
    }



    private AttendanceResponse handleCheckOut(AttendanceLog log, LocalDateTime checkoutTime) {
        if (!log.getCheckinTime().toLocalDate().isEqual(checkoutTime.toLocalDate())) {
            throw new AppException(ErrorCode.INVALID_CHECKOUT_DATE);
        }

        log.setCheckoutTime(checkoutTime);
        double workHours = Duration.between(log.getCheckinTime(), checkoutTime).toMinutes() / 60.0;
        log.setWorkHours(workHours);

        Employee employee = log.getEmployee();

        if (employee.getEmploymentType() == EmploymentType.PART_TIME) {
            List<Assignment> assignments = assignmentRepository.findByEmployeeAndDate(employee, checkoutTime.toLocalDate());

            if (assignments.isEmpty()) {
                throw new AppException(ErrorCode.NO_SHIFT_ASSIGNED_TODAY);
            }

            Assignment assignment = assignments.stream()
                    .filter(a -> a.getDate().isEqual(checkoutTime.toLocalDate()))  // ✅ Chỉ checkout đúng ngày
                    .filter(a -> a.getShift().getEndTime().isAfter(checkoutTime.toLocalTime()))
                    .min(Comparator.comparing(a -> a.getShift().getStartTime()))
                    .orElseThrow(() -> new AppException(ErrorCode.NO_VALID_SHIFT_FOR_CHECKOUT));

            LocalTime shiftEndTime = assignment.getShift().getEndTime();
            double expectedWorkHours = Duration.between(assignment.getShift().getStartTime(), shiftEndTime).toMinutes() / 60.0;
            double minimumRequiredHours = expectedWorkHours * 0.75;

            if (checkoutTime.toLocalTime().isBefore(shiftEndTime) || workHours < minimumRequiredHours) {
                log.setStatus(AttendanceStatus.EARLY_CHECKOUT);
            } else {
                log.setStatus(AttendanceStatus.VALID);
            }
        } else {
            throw new AppException(ErrorCode.INVALID_EMPLOYMENT_TYPE);
        }

        return attendanceMapper.toResponse(attendanceLogRepository.save(log));
    }



    private User getUserFromToken() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXIST));
    }

    private void validateQrCode(Long qrCodeId, String nonce) {
        qrCodeRepository.findByIdAndNonce(qrCodeId, nonce)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_QRCODE));
    }
}
