package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.azebrow.harvest.model.Job;
import pl.azebrow.harvest.model.Payment;
import pl.azebrow.harvest.settlement.EmployeeSettlement;
import pl.azebrow.harvest.settlement.LocationSettlement;
import pl.azebrow.harvest.settlement.LocationsSettlement;
import pl.azebrow.harvest.settlement.datasource.EmployeeSettlementDatasource;
import pl.azebrow.harvest.settlement.datasource.LocationsSettlementDatasource;
import pl.azebrow.harvest.specification.SpecificationBuilder;
import pl.azebrow.harvest.utils.JxlsUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private static final String EMPLOYEE_REPORT_TEMPLATE_PATH = "templates/employee_report_template.xls";
    private static final String LOCATION_REPORT_TEMPLATE_PATH = "templates/location_report_template.xls";
    private final EmployeeService employeeService;
    private final JobService jobService;
    private final PaymentService paymentService;

    private final JxlsUtils jxlsUtils;

    public byte[] generateEmployeeSettlementReport(Long employeeId,
                                                   LocalDate from,
                                                   LocalDate to) {
        var settlement = getEmployeeSettlement(employeeId, from, to);
        var datasource = new EmployeeSettlementDatasource(settlement);
        return jxlsUtils.generateReport(EMPLOYEE_REPORT_TEMPLATE_PATH, datasource);
    }

    public byte[] generateLocationSettlementReport(LocalDate dateFrom, LocalDate dateTo) {
        var settlement = getLocationsSettlement(dateFrom, dateTo);
        var datasource = new LocationsSettlementDatasource(settlement);
        return jxlsUtils.generateReport(LOCATION_REPORT_TEMPLATE_PATH, datasource);
    }

    public EmployeeSettlement getEmployeeSettlement(Long employeeId,
                                                    LocalDate dateFrom,
                                                    LocalDate dateTo) {
        var employee = employeeService.getEmployeeById(employeeId); // check for employee existence
        var specs = new SpecificationBuilder()
                .with(SpecificationBuilder.DATE_FROM, dateFrom.toString())
                .with(SpecificationBuilder.DATE_TO, dateTo.toString())
                .with(SpecificationBuilder.EMPLOYEE, employeeId);
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

    private LocationsSettlement getLocationsSettlement(LocalDate dateFrom, LocalDate dateTo) {
        var specs = new SpecificationBuilder()
                .with(SpecificationBuilder.DATE_FROM, dateFrom.toString())
                .with(SpecificationBuilder.DATE_TO, dateTo.toString());
        var jobs = jobService.findJobs(specs.build(Job.class));
        var map = jobs.stream()
                .collect(Collectors.groupingBy(Job::getLocation));
        var locationSettlementList = map.entrySet().stream()
                .map(e -> new LocationSettlement(e.getKey(), e.getValue()))
                .toList();
        var totalAmount = locationSettlementList.stream()
                .map(LocationSettlement::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return LocationsSettlement.builder()
                .reportGenerationTime(LocalDateTime.now())
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .locationSettlements(locationSettlementList)
                .totalAmount(totalAmount)
                .build();
    }
}
