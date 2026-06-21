package com.sems.payrollservice.controller;

import com.sems.payrollservice.model.Payroll;
import com.sems.payrollservice.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payroll")
@CrossOrigin(origins = "*")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // GET all payroll records
    @GetMapping
    public ResponseEntity<List<Payroll>> getAllPayroll() {
        List<Payroll> payrolls = payrollService.getAllPayroll();
        return ResponseEntity.ok(payrolls);
    }

    // GET payroll by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payroll> getPayrollById(@PathVariable Long id) {
        Optional<Payroll> payroll = payrollService.getPayrollById(id);
        if (payroll.isPresent()) {
            return ResponseEntity.ok(payroll.get());
        }
        return ResponseEntity.notFound().build();
    }

    // GET payroll by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Payroll>> getPayrollByEmployeeId(@PathVariable Long employeeId) {
        List<Payroll> payrolls = payrollService.getPayrollByEmployeeId(employeeId);
        return ResponseEntity.ok(payrolls);
    }

    // GET payroll by month
    @GetMapping("/month/{month}")
    public ResponseEntity<List<Payroll>> getPayrollByMonth(@PathVariable String month) {
        List<Payroll> payrolls = payrollService.getPayrollByMonth(month);
        return ResponseEntity.ok(payrolls);
    }

    // GET payroll by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payroll>> getPayrollByStatus(@PathVariable String status) {
        List<Payroll> payrolls = payrollService.getPayrollByStatus(status);
        return ResponseEntity.ok(payrolls);
    }

    // GET payroll by employee and status
    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<Payroll>> getPayrollByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @PathVariable String status) {
        List<Payroll> payrolls = payrollService.getPayrollByEmployeeAndStatus(employeeId, status);
        return ResponseEntity.ok(payrolls);
    }

    // GET payroll by employee and month
    @GetMapping("/employee/{employeeId}/month/{month}")
    public ResponseEntity<Payroll> getPayrollByEmployeeAndMonth(
            @PathVariable Long employeeId,
            @PathVariable String month) {
        Optional<Payroll> payroll = payrollService.getPayrollByEmployeeAndMonth(employeeId, month);
        if (payroll.isPresent()) {
            return ResponseEntity.ok(payroll.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Calculate and create payroll
    @PostMapping("/calculate")
    public ResponseEntity<Payroll> calculatePayroll(
            @RequestParam Long employeeId,
            @RequestParam String month,
            @RequestParam Double basicSalary,
            @RequestParam Double hra,
            @RequestParam Double dearness,
            @RequestParam Double deductions) {
        Payroll payroll = payrollService.calculatePayroll(employeeId, month, basicSalary, hra, dearness, deductions);
        return ResponseEntity.status(HttpStatus.CREATED).body(payroll);
    }

    // POST - Generate payroll
    @PostMapping
    public ResponseEntity<Payroll> generatePayroll(@RequestBody Payroll payroll) {
        Payroll createdPayroll = payrollService.generatePayroll(payroll);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayroll);
    }

    // POST - Process payroll
    @PostMapping("/{id}/process")
    public ResponseEntity<Payroll> processPayroll(@PathVariable Long id) {
        Payroll payroll = payrollService.processPayroll(id);
        if (payroll != null) {
            return ResponseEntity.ok(payroll);
        }
        return ResponseEntity.notFound().build();
    }

    // POST - Mark as paid
    @PostMapping("/{id}/pay")
    public ResponseEntity<Payroll> markAsPaid(@PathVariable Long id) {
        Payroll payroll = payrollService.markAsPaid(id);
        if (payroll != null) {
            return ResponseEntity.ok(payroll);
        }
        return ResponseEntity.notFound().build();
    }

    // PUT - Update payroll
    @PutMapping("/{id}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable Long id, @RequestBody Payroll payroll) {
        Payroll updatedPayroll = payrollService.updatePayroll(id, payroll);
        if (updatedPayroll != null) {
            return ResponseEntity.ok(updatedPayroll);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE payroll
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayroll(@PathVariable Long id) {
        Optional<Payroll> payroll = payrollService.getPayrollById(id);
        if (payroll.isPresent()) {
            payrollService.deletePayroll(id);
            return ResponseEntity.ok("Payroll with ID " + id + " deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

}