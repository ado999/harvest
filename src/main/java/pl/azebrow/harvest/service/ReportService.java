package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;
import pl.azebrow.harvest.settlement.EmployeeSettlement;
import pl.azebrow.harvest.settlement.datasource.EmployeeSettlementDataSource;
import pl.azebrow.harvest.specification.SpecificationBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public void generateEmployeeSettlementReport(Long employeeId,
                                                 String from,
                                                 String to) {
        var settlement = getEmployeeSettlement(employeeId, from, to);
        var datasource = new EmployeeSettlementDataSource(settlement);
        try (InputStream is = new ClassPathResource("templates/employee_report_template.xls").getInputStream()) {
            var f = new File("result.xls");
            f.createNewFile();
            var os = new FileOutputStream(f, false);
            Context ctx = new Context();
            ctx.putVar("ds", datasource);
            JxlsHelper.getInstance().processTemplateAtCell(is, os, ctx, "Result!A1");
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                .reportGenerationTime(LocalDateTime.now())
                .build();
    }
}
