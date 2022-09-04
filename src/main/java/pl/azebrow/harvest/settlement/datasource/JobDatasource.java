package pl.azebrow.harvest.settlement.datasource;

import lombok.Getter;
import pl.azebrow.harvest.model.Job;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class JobDatasource {
    private final String date;
    private final String employee;
    private final String location;
    private final String uom;
    private final BigDecimal quantity;
    private final BigDecimal rate;
    private final BigDecimal totalAmount;

    public JobDatasource(Job job) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date = job.getDate().format(dateFormat);
        var employeeAccount = job.getEmployee().getAccount();
        employee = String.format("%s %s", employeeAccount.getFirstName(), employeeAccount.getLastName());
        location = job.getLocation().getDescription();
        uom = job.getJobType().getUnit().getSuffix();
        quantity = job.getQuantity();
        rate = job.getRate();
        totalAmount = job.getTotalAmount();
    }

}
