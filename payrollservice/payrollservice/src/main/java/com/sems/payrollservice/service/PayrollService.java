package com.sems.payrollservice.service;

import com.sems.payrollservice.model.Payroll;
import com.sems.payrollservice.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    // Get all payroll records
    public List<Payroll> getAllPayroll() {
        return payrollRepository.findAll();
    }

    // Get payroll by ID
    public Optional<Payroll> getPayrollById(Long id) {
        return payrollRepository.findById(id);
    }

    // Get payroll by employee ID
    public List<Payroll> getPayrollByEmployeeId(Long employeeId) {
        return payrollRepository.findByEmployeeId(employeeId);
    }

    // Get payroll by month
    public List<Payroll> getPayrollByMonth(String month) {
        return payrollRepository.findByMonth(month);
    }

    // Get payroll by status
    public List<Payroll> getPayrollByStatus(String status) {
        return payrollRepository.findByStatus(status);
    }

    // Get payroll by employee and status
    public List<Payroll> getPayrollByEmployeeAndStatus(Long employeeId, String status) {
        return payrollRepository.findByEmployeeIdAndStatus(employeeId, status);
    }

    // Get payroll by employee and month
    public Optional<Payroll> getPayrollByEmployeeAndMonth(Long employeeId, String month) {
        return payrollRepository.findByEmployeeIdAndMonth(employeeId, month);
    }

    // Calculate payroll
    public Payroll calculatePayroll(Long employeeId, String month, Double basicSalary,
                                    Double hra, Double dearness, Double deductions) {
        Payroll payroll = new Payroll();
        payroll.setEmployeeId(employeeId);
        payroll.setMonth(month);
        payroll.setBasicSalary(basicSalary);
        payroll.setHra(hra);
        payroll.setDearness(dearness);
        payroll.setDeductions(deductions);

        Double grossSalary = basicSalary + hra + dearness;
        Double netSalary = grossSalary - deductions;

        payroll.setGrossSalary(grossSalary);
        payroll.setNetSalary(netSalary);
        payroll.setStatus("PENDING");

        return payrollRepository.save(payroll);
    }

    // Generate payroll
    public Payroll generatePayroll(Payroll payroll) {
        Double grossSalary = payroll.getBasicSalary() + payroll.getHra() + payroll.getDearness();
        Double netSalary = grossSalary - payroll.getDeductions();

        payroll.setGrossSalary(grossSalary);
        payroll.setNetSalary(netSalary);
        payroll.setStatus("PENDING");

        return payrollRepository.save(payroll);
    }

    // Process payroll
    public Payroll processPayroll(Long id) {
        Optional<Payroll> payroll = payrollRepository.findById(id);
        if (payroll.isPresent()) {
            Payroll p = payroll.get();
            p.setStatus("PROCESSED");
            return payrollRepository.save(p);
        }
        return null;
    }

    // Mark as paid
    public Payroll markAsPaid(Long id) {
        Optional<Payroll> payroll = payrollRepository.findById(id);
        if (payroll.isPresent()) {
            Payroll p = payroll.get();
            p.setStatus("PAID");
            p.setPaymentDate(LocalDate.now());
            return payrollRepository.save(p);
        }
        return null;
    }

    // Update payroll
    public Payroll updatePayroll(Long id, Payroll payrollDetails) {
        Optional<Payroll> payroll = payrollRepository.findById(id);
        if (payroll.isPresent()) {
            Payroll p = payroll.get();
            p.setEmployeeId(payrollDetails.getEmployeeId());
            p.setMonth(payrollDetails.getMonth());
            p.setBasicSalary(payrollDetails.getBasicSalary());
            p.setHra(payrollDetails.getHra());
            p.setDearness(payrollDetails.getDearness());
            p.setDeductions(payrollDetails.getDeductions());

            Double grossSalary = p.getBasicSalary() + p.getHra() + p.getDearness();
            Double netSalary = grossSalary - p.getDeductions();
            p.setGrossSalary(grossSalary);
            p.setNetSalary(netSalary);

            return payrollRepository.save(p);
        }
        return null;
    }

    // Delete payroll
    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }

}