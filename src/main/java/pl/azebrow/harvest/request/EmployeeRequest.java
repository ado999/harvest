package pl.azebrow.harvest.request;

import lombok.Data;

@Data
public class EmployeeRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean passportTaken;

    public AccountRequest toAccountRequest() {
        var request = new AccountRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        return request;
    }

}
