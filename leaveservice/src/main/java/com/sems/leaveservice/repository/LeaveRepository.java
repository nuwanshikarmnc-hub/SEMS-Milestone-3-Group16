package com.sems.leaveservice.repository;

import com.sems.leaveservice.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeId(Long employeeId);
    List<Leave> findByStatus(String status);
    List<Leave> findByEmployeeIdAndStatus(Long employeeId, String status);
    List<Leave> findByLeaveType(String leaveType);
    List<Leave> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}