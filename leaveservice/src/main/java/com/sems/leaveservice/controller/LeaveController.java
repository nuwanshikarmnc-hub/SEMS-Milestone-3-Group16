package com.sems.leaveservice.controller;

import com.sems.leaveservice.model.Leave;
import com.sems.leaveservice.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // GET all leaves
    @GetMapping
    public ResponseEntity<List<Leave>> getAllLeaves() {
        List<Leave> leaves = leaveService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }

    // GET leave by ID
    @GetMapping("/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Long id) {
        Optional<Leave> leave = leaveService.getLeaveById(id);
        if (leave.isPresent()) {
            return ResponseEntity.ok(leave.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET leaves by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Leave>> getLeavesByEmployeeId(@PathVariable Long employeeId) {
        List<Leave> leaves = leaveService.getLeavesByEmployeeId(employeeId);
        return ResponseEntity.ok(leaves);
    }

    // GET leaves by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Leave>> getLeavesByStatus(@PathVariable String status) {
        List<Leave> leaves = leaveService.getLeavesByStatus(status);
        return ResponseEntity.ok(leaves);
    }

    // GET leaves by employee and status
    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<Leave>> getLeavesByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @PathVariable String status) {
        List<Leave> leaves = leaveService.getLeavesByEmployeeAndStatus(employeeId, status);
        return ResponseEntity.ok(leaves);
    }

    // GET leaves by type
    @GetMapping("/type/{leaveType}")
    public ResponseEntity<List<Leave>> getLeavesByType(@PathVariable String leaveType) {
        List<Leave> leaves = leaveService.getLeavesByType(leaveType);
        return ResponseEntity.ok(leaves);
    }

    // GET leaves by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<Leave>> getLeavesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Leave> leaves = leaveService.getLeavesByDateRange(startDate, endDate);
        return ResponseEntity.ok(leaves);
    }

    // POST - Submit leave request
    @PostMapping
    public ResponseEntity<Leave> submitLeaveRequest(@RequestBody Leave leave) {
        Leave createdLeave = leaveService.submitLeaveRequest(leave);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLeave);
    }

    // POST - Approve leave
    @PostMapping("/{id}/approve")
    public ResponseEntity<Leave> approveLeave(
            @PathVariable Long id,
            @RequestParam Long approverId,
            @RequestParam(required = false) String comment) {
        Leave leave = leaveService.approveLeave(id, approverId, comment);
        if (leave != null) {
            return ResponseEntity.ok(leave);
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Reject leave
    @PostMapping("/{id}/reject")
    public ResponseEntity<Leave> rejectLeave(
            @PathVariable Long id,
            @RequestParam Long approverId,
            @RequestParam(required = false) String comment) {
        Leave leave = leaveService.rejectLeave(id, approverId, comment);
        if (leave != null) {
            return ResponseEntity.ok(leave);
        }
        return ResponseEntity.notFound().build();
    }

    // PUT - Update leave
    @PutMapping("/{id}")
    public ResponseEntity<Leave> updateLeave(@PathVariable Long id, @RequestBody Leave leave) {
        Leave updatedLeave = leaveService.updateLeave(id, leave);
        if (updatedLeave != null) {
            return ResponseEntity.ok(updatedLeave);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE leave
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeave(@PathVariable Long id) {
        Optional<Leave> leave = leaveService.getLeaveById(id);
        if (leave.isPresent()) {
            leaveService.deleteLeave(id);
            return ResponseEntity.ok("Leave request with ID " + id + " deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

}