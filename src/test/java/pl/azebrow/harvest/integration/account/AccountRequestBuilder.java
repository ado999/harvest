package pl.azebrow.harvest.integration.account;

import pl.azebrow.harvest.request.AccountEmailUpdateRequest;
import pl.azebrow.harvest.request.AccountRequest;
import pl.azebrow.harvest.request.AccountUpdateRequest;

public class AccountRequestBuilder {

    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;

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

    public AccountRequestBuilder enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public AccountRequest accountRequest() {
        var request = new AccountRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        return request;
    }

    public AccountUpdateRequest accountUpdateRequest() {
        var request = new AccountUpdateRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEnabled(enabled);
        return request;
    }

    public AccountEmailUpdateRequest emailUpdateRequest() {
        var request = new AccountEmailUpdateRequest();
        request.setEmail(email);
        return request;
    }
}
