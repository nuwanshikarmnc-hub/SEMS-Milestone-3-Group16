package com.sems.attendanceservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = true)
    private LocalTime clockIn;

    @Column(nullable = true)
    private LocalTime clockOut;

    @Column(nullable = false)
    private String status; // PRESENT, ABSENT, LATE, ON_LEAVE

}