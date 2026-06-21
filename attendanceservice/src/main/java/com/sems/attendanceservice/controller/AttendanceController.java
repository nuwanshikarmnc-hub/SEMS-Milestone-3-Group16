package com.sems.attendanceservice.controller;

import com.sems.attendanceservice.model.Attendance;
import com.sems.attendanceservice.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // GET all attendance records
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        List<Attendance> attendanceList = attendanceService.getAllAttendance();
        return ResponseEntity.ok(attendanceList);
    }

    // GET attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        if (attendance.isPresent()) {
            return ResponseEntity.ok(attendance.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET attendance by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployeeId(@PathVariable Long employeeId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByEmployeeId(employeeId);
        return ResponseEntity.ok(attendanceList);
    }

    // GET attendance by employee ID and date
    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId,
            @RequestParam LocalDate date) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByEmployeeAndDate(employeeId, date);
        return ResponseEntity.ok(attendanceList);
    }

    // GET attendance by date
    @GetMapping("/date")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@RequestParam LocalDate date) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(attendanceList);
    }

    // GET attendance by status
    @GetMapping("/employee/{employeeId}/status")
    public ResponseEntity<List<Attendance>> getAttendanceByStatus(
            @PathVariable Long employeeId,
            @RequestParam String status) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByStatus(employeeId, status);
        return ResponseEntity.ok(attendanceList);
    }

    // Clock In
    @PostMapping("/clock-in")
    public ResponseEntity<Attendance> clockIn(
            @RequestParam Long employeeId,
            @RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        Attendance attendance = attendanceService.clockIn(employeeId, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }

    // Clock Out
    @PostMapping("/clock-out")
    public ResponseEntity<Attendance> clockOut(
            @RequestParam Long employeeId,
            @RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        Attendance attendance = attendanceService.clockOut(employeeId, date);
        if (attendance != null) {
            return ResponseEntity.ok(attendance);
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Mark attendance manually
    @PostMapping
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        Attendance createdAttendance = attendanceService.markAttendance(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
    }

    // PUT - Update attendance
    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        Attendance updatedAttendance = attendanceService.updateAttendance(id, attendance);
        if (updatedAttendance != null) {
            return ResponseEntity.ok(updatedAttendance);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE - Remove attendance record
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        if (attendance.isPresent()) {
            attendanceService.deleteAttendance(id);
            return ResponseEntity.ok("Attendance record with ID " + id + " deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

}