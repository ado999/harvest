package pl.azebrow.harvest.settlement.datasource;

import lombok.Getter;
import pl.azebrow.harvest.model.Payment;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Getter
public class PaymentDataSource {
    private final String date;
    private final BigDecimal amount;

    public PaymentDataSource(Payment payment) {
        var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date = payment.getCreatedDate().format(dateFormatter);
        amount = payment.getAmount();
    }
}
