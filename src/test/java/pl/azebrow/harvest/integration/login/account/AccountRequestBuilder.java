package pl.azebrow.harvest.integration.login.account;

import pl.azebrow.harvest.request.AccountRequest;

public class AccountRequestBuilder {

    private String firstName;
    private String lastName;
    private String email;

    public static AccountRequestBuilder builder() {
        return new AccountRequestBuilder();
    }

    public AccountRequestBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountRequestBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountRequestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public AccountRequest build() {
        var request = new AccountRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        return request;
    }
}
