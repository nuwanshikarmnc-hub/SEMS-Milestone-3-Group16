package com.sems.leaveservice.service;

import com.sems.leaveservice.model.Leave;
import com.sems.leaveservice.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    // Get all leave requests
    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // Get leave by ID
    public Optional<Leave> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    // Get leaves by employee ID
    public List<Leave> getLeavesByEmployeeId(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    // Get leaves by status
    public List<Leave> getLeavesByStatus(String status) {
        return leaveRepository.findByStatus(status);
    }

    // Get leaves by employee and status
    public List<Leave> getLeavesByEmployeeAndStatus(Long employeeId, String status) {
        return leaveRepository.findByEmployeeIdAndStatus(employeeId, status);
    }

    // Get leaves by type
    public List<Leave> getLeavesByType(String leaveType) {
        return leaveRepository.findByLeaveType(leaveType);
    }

    // Get leaves by date range
    public List<Leave> getLeavesByDateRange(LocalDate startDate, LocalDate endDate) {
        return leaveRepository.findByStartDateBetween(startDate, endDate);
    }

    // Submit leave request
    public Leave submitLeaveRequest(Leave leave) {
        leave.setStatus("PENDING");
        return leaveRepository.save(leave);
    }

    // Approve leave request
    public Leave approveLeave(Long id, Long approverId, String comment) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (leave.isPresent()) {
            Leave leaveRequest = leave.get();
            leaveRequest.setStatus("APPROVED");
            leaveRequest.setApproverId(approverId);
            leaveRequest.setApproverComment(comment);
            return leaveRepository.save(leaveRequest);
        }
        return null;
    }

    // Reject leave request
    public Leave rejectLeave(Long id, Long approverId, String comment) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (leave.isPresent()) {
            Leave leaveRequest = leave.get();
            leaveRequest.setStatus("REJECTED");
            leaveRequest.setApproverId(approverId);
            leaveRequest.setApproverComment(comment);
            return leaveRepository.save(leaveRequest);
        }
        return null;
    }

    // Update leave request
    public Leave updateLeave(Long id, Leave leaveDetails) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (leave.isPresent()) {
            Leave leaveRequest = leave.get();
            leaveRequest.setEmployeeId(leaveDetails.getEmployeeId());
            leaveRequest.setLeaveType(leaveDetails.getLeaveType());
            leaveRequest.setStartDate(leaveDetails.getStartDate());
            leaveRequest.setEndDate(leaveDetails.getEndDate());
            leaveRequest.setReason(leaveDetails.getReason());
            leaveRequest.setStatus(leaveDetails.getStatus());
            return leaveRepository.save(leaveRequest);
        }
        return null;
    }

    // Delete leave request
    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }

}