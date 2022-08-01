package pl.azebrow.harvest.response;

import java.util.Collection;

public class EmployeeResponse {
    private AccountResponse account;
    private Collection<InsuranceResponse> policies;
    private InsuranceStatus insuranceStatus;
    private String code;

}
