package pl.azebrow.harvest.request;

import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    private String phoneNumber;
    private Boolean passportTaken;
}
