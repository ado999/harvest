package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;
import pl.azebrow.harvest.settlement.EmployeeSettlement;
import pl.azebrow.harvest.settlement.datasource.EmployeeSettlementDataSource;
import pl.azebrow.harvest.specification.SpecificationBuilder;
import pl.azebrow.harvest.utils.JxlsUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final EmployeeService employeeService;
    private final JobService jobService;
    private final PaymentService paymentService;

    private final JxlsUtils jxlsUtils;

    public byte[] generateEmployeeSettlementReport(Long employeeId,
                                                   String from,
                                                   String to) {
        var settlement = getEmployeeSettlement(employeeId, from, to);
        var datasource = new EmployeeSettlementDataSource(settlement);
        return jxlsUtils.generateReport("templates/employee_report_template.xls", datasource);
    }

    public EmployeeSettlement getEmployeeSettlement(Long employeeId,
                                                    String from,
                                                    String to) {
        var dateFrom = LocalDate.parse(from);
        var dateTo = LocalDate.parse(to);
        var employee = employeeService.getEmployeeById(employeeId); // check for employee existence
        var specs = new SpecificationBuilder()
                .with("dateFrom", from)
                .with("dateTo", to)
                .with("employee", employeeId);
        var jobs = jobService.findJobs(specs.build(Job.class));
        var payments = paymentService.findPayments(specs.build(Payment.class));
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
                .reportGenerationTime(LocalDateTime.now())
                .build();
    }
}
