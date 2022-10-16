package pl.azebrow.harvest.integration.insurance;

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class InsuranceRequestBuilder {

    private Long employeeId = 1L;
    private LocalDate validFrom = LocalDate.now().minus(1L, ChronoUnit.MONTHS);
    private LocalDate validTo = LocalDate.now().plus(1L, ChronoUnit.MONTHS);

    public InsuranceRequestBuilder employeeId(Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public InsuranceRequestBuilder validFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public InsuranceRequestBuilder validTo(LocalDate validTo) {
        this.validTo = validTo;
        return this;
    }

    public SimpleInsuranceRequest insuranceRequest() {
        var request = new SimpleInsuranceRequest();
        request.setEmployeeId(employeeId);
        request.setValidFrom(validFrom);
        request.setValidTo(validTo);
        return request;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    @Data
    public class SimpleInsuranceRequest {
        private Long employeeId;
        private LocalDate validFrom;
        private LocalDate validTo;

        public String getValidFrom() {
            return validFrom.toString();
        }

        public String getValidTo() {
            return validTo.toString();
        }
    }
}
