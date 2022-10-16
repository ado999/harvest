package pl.azebrow.harvest.integration.employee;

import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.EmployeeRequest;
import pl.azebrow.harvest.request.EmployeeUpdateRequest;

public class EmployeeRequestBuilder {

    private String firstName = "First";
    private String lastName = "Last";
    private String email = "employee@admin.qp";
    private String phoneNumber = "123456789";
    private Boolean passportTaken = true;

    public EmployeeRequestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeRequestBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeRequestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeRequestBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public EmployeeRequestBuilder passportTaken(Boolean passportTaken) {
        this.passportTaken = passportTaken;
        return this;
    }

    public EmployeeRequest employeeRequest() {
        var request = new EmployeeRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setPhoneNumber(phoneNumber);
        request.setPassportTaken(passportTaken);
        return request;
    }

    public AccountRequest accountRequest() {
        return employeeRequest().toAccountRequest();
    }

    public EmployeeUpdateRequest updateRequest() {
        var request = new EmployeeUpdateRequest();
        request.setPhoneNumber(phoneNumber);
        request.setPassportTaken(passportTaken);
        return request;
    }

}
