package pl.azebrow.harvest.response;

import lombok.Data;

import java.util.Collection;

@Data
public class EmployeeResponse {
    private AccountResponse account;
    private Collection<InsuranceResponse> policies;
    private String code;

}
