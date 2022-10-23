package pl.azebrow.harvest.integration.payment;

import pl.azebrow.harvest.request.PaymentRequest;

import java.math.BigDecimal;

public class PaymentRequestBuilder {
    private Long employeeId;
    private BigDecimal amount;

    public PaymentRequestBuilder employeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public PaymentRequestBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public PaymentRequest paymentRequest() {
        var request = new PaymentRequest();
        request.setEmployeeId(employeeId);
        request.setAmount(amount);
        return request;
    }
}
