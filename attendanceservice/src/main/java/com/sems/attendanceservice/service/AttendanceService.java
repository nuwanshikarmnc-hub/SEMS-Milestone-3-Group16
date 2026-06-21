package com.sems.attendanceservice.service;

import com.sems.attendanceservice.model.Attendance;
import com.sems.attendanceservice.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // Get attendance by ID
    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    // Get attendance by employee ID
    public List<Attendance> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    // Get attendance by employee ID and date
    public List<Attendance> getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        return attendanceRepository.findByEmployeeIdAndDate(employeeId, date);
    }

    // Get attendance by date
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    // Get attendance by status
    public List<Attendance> getAttendanceByStatus(Long employeeId, String status) {
        return attendanceRepository.findByEmployeeIdAndStatus(employeeId, status);
    }

    // Clock in
    public Attendance clockIn(Long employeeId, LocalDate date) {
        Optional<Attendance> existingRecord = attendanceRepository.findByEmployeeIdAndDate(employeeId, date)
                .stream().findFirst();

        if (existingRecord.isPresent()) {
            Attendance attendance = existingRecord.get();
            attendance.setClockIn(java.time.LocalTime.now());
            attendance.setStatus("PRESENT");
            return attendanceRepository.save(attendance);
        } else {
            Attendance attendance = new Attendance();
            attendance.setEmployeeId(employeeId);
            attendance.setDate(date);
            attendance.setClockIn(java.time.LocalTime.now());
            attendance.setStatus("PRESENT");
            return attendanceRepository.save(attendance);
        }
    }

    // Clock out
    public Attendance clockOut(Long employeeId, LocalDate date) {
        Optional<Attendance> existingRecord = attendanceRepository.findByEmployeeIdAndDate(employeeId, date)
                .stream().findFirst();

        if (existingRecord.isPresent()) {
            Attendance attendance = existingRecord.get();
            attendance.setClockOut(java.time.LocalTime.now());
            return attendanceRepository.save(attendance);
        }
        return null;
    }

    // Mark attendance
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    // Update attendance
    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isPresent()) {
            Attendance att = attendance.get();
            att.setEmployeeId(attendanceDetails.getEmployeeId());
            att.setDate(attendanceDetails.getDate());
            att.setClockIn(attendanceDetails.getClockIn());
            att.setClockOut(attendanceDetails.getClockOut());
            att.setStatus(attendanceDetails.getStatus());
            return attendanceRepository.save(att);
        }
        return null;
    }

    // Delete attendance
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

}