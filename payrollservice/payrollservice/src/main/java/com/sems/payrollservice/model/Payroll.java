package com.sems.payrollservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "payroll")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "month", nullable = false)
    private String month; // Format: YYYY-MM (e.g., 2026-06)

    @Column(name = "basic_salary", nullable = false)
    private Double basicSalary;

    @Column(name = "hra", nullable = false)
    private Double hra; // House Rent Allowance

    @Column(name = "dearness", nullable = false)
    private Double dearness; // Dearness Allowance

    @Column(name = "deductions", nullable = false)
    private Double deductions;

    @Column(name = "gross_salary", nullable = false)
    private Double grossSalary;

    @Column(name = "net_salary", nullable = false)
    private Double netSalary;

    @Column(name = "status", nullable = true)
    private String status; // PENDING, PROCESSED, PAID

    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;

}