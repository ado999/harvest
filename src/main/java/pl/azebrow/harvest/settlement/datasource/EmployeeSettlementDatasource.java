package pl.azebrow.harvest.settlement.datasource;

import lombok.Getter;
import pl.azebrow.harvest.settlement.EmployeeSettlement;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class EmployeeSettlementDatasource {
    private final String generationTime;
    private final String generationDate;
    private final String employeeFirstName;
    private final String employeeLastName;
    private final String employeeEmail;
    private final String dateFrom;
    private final String dateTo;
    private final Collection<JobDatasource> jobs;
    private final BigDecimal jobsAmount;
    private final Collection<PaymentDatasource> payments;
    private final BigDecimal paymentsAmount;
    private final BigDecimal totalAmount;
    private final BigDecimal totalBalance;

    public EmployeeSettlementDatasource(EmployeeSettlement settlement) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        generationDate = settlement.getReportGenerationTime().format(dateFormat);
        generationTime = settlement.getReportGenerationTime().format(timeFormat);
        employeeFirstName = settlement.getEmployee().getAccount().getFirstName();
        employeeLastName = settlement.getEmployee().getAccount().getLastName();
        employeeEmail = settlement.getEmployee().getAccount().getEmail();
        dateFrom = settlement.getDateFrom().format(dateFormat);
        dateTo = settlement.getDateTo().format(dateFormat);
        jobs = settlement.getJobs().stream().map(JobDatasource::new).collect(Collectors.toList());
        jobsAmount = settlement.getJobsAmount();
        payments = settlement.getPayments().stream().map(PaymentDatasource::new).collect(Collectors.toList());
        paymentsAmount = settlement.getPaymentsAmount();
        totalAmount = settlement.getTotalAmount();
        totalBalance = settlement.getTotalBalance();
    }
}
