package pl.azebrow.harvest.response;

import lombok.Data;

@Data
public class EmployeeResponse {
    private AccountResponse account;
    private String code;
    private String phoneNumber;
    private Boolean passportTaken;
}
