package com.example.qrcodecheckin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "departments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    List<Employee> employees;
}
