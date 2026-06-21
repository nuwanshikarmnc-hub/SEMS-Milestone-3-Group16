package com.sems.payrollservice.repository;

import com.sems.payrollservice.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmployeeId(Long employeeId);
    List<Payroll> findByMonth(String month);
    List<Payroll> findByStatus(String status);
    Optional<Payroll> findByEmployeeIdAndMonth(Long employeeId, String month);
    List<Payroll> findByEmployeeIdAndStatus(Long employeeId, String status);
}