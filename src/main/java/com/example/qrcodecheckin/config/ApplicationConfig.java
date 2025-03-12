package com.example.qrcodecheckin.config;

import com.example.qrcodecheckin.entity.Role;
import com.example.qrcodecheckin.entity.Shift;
import com.example.qrcodecheckin.entity.User;
import com.example.qrcodecheckin.enums.RoleEnum;
import com.example.qrcodecheckin.enums.ShiftEnum;
import com.example.qrcodecheckin.repository.RoleRepository;
import com.example.qrcodecheckin.repository.ShiftRepository;
import com.example.qrcodecheckin.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationConfig {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ShiftRepository shiftRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            //Create init roles
            Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).orElseGet(() ->
                    roleRepository.save(Role.builder().name(RoleEnum.ADMIN).build())
            );
            roleRepository.findByName(RoleEnum.USER).orElseGet(() ->
                    roleRepository.save(Role.builder().name(RoleEnum.USER).build())
            );
            // Create init shifts
            Set<ShiftEnum> shifts = Set.of(
                    ShiftEnum.MORNING,
                    ShiftEnum.AFTERNOON,
                    ShiftEnum.NIGHT,
                    ShiftEnum.FULL_DAY
            );
            shifts.forEach(shift -> {
                shiftRepository.findByName(shift.getName())
                        .orElseGet(() -> shiftRepository.save(
                                Shift.builder()
                                        .name(shift.getName())
                                        .startTime(shift.getStartTime())
                                        .endTime(shift.getEndTime())
                                        .requiredHours(shift.getRequiredHours())
                                        .build()
                        ));
            });

            //Create admin account
            if (!userRepository.existsByUsername("admin")) {
                userRepository.save(User
                        .builder()
                        .firstName("admin")
                        .lastName("admin")
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(adminRole))
                        .build()
                );
                log.warn("Admin account has been created with default username and password is admin please change password");
            }
        };
    }
}
