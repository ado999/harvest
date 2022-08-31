package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;
import pl.azebrow.harvest.settlement.EmployeeSettlement;
import pl.azebrow.harvest.specification.SpecificationBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeService employeeService;
    private final JobService jobService;
    private final PaymentService paymentService;
    private final SettlementService settlementService;
    private final LocationService locationService;

    public EmployeeSettlement getEmployeeSettlement(Long employeeId,
                                                    String from,
                                                    String to) {
        var dateFrom = LocalDate.parse(from);
        var dateTo = LocalDate.parse(to);
        var employee = employeeService.getEmployeeById(employeeId); // check for employee existence
        var jobSpecs = new SpecificationBuilder<>(Job.class)
                .with("dateFrom", from)
                .with("dateTo", to)
                .with("employee", employeeId);
        var builtJobSpecs = jobSpecs.build();
        var jobs = jobService.findJobs(builtJobSpecs);
        var paySpecs = new SpecificationBuilder<>(Payment.class)
                .with("dateFrom", from)
                .with("dateTo", to)
                .with("employee", employeeId);
        var builtPaySpecs = paySpecs.build();
        var payments = paymentService.findPayments(builtPaySpecs);
        var jobsAmount = jobs.stream()
                .map(Job::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var paymentsAmount = payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return EmployeeSettlement.builder()
                .employee(employee)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .jobs(jobs)
                .jobsAmount(jobsAmount)
                .payments(payments)
                .paymentsAmount(paymentsAmount)
                .totalAmount(jobsAmount.add(paymentsAmount))
                .totalBalance(employee.getBalance())
                .build();
    }
}
