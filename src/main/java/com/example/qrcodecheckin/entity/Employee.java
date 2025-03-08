package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "employees")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false, unique = true)
    @Email
    String email;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    Department department;
}
