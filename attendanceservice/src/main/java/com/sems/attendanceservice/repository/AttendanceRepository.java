package com.sems.attendanceservice.repository;

import com.sems.attendanceservice.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
    List<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByEmployeeIdAndStatus(Long employeeId, String status);
}