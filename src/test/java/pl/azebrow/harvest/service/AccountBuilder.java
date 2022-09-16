package pl.azebrow.harvest.service;

import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.AccountStatus;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Role;

import java.util.Collection;
import java.util.List;

public class AccountBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private AccountStatus status;
    private Employee employee;
    private Collection<Role> roles;

    public static AccountBuilder account(){
        return new AccountBuilder();
    }

    public AccountBuilder(){
        firstName = "First";
        lastName = "Last";
        email = "e@ma.il";
        password = "password";
        enabled = true;
        status = AccountStatus.EMAIL_CONFIRMED;
        employee = null;
        roles = List.of(new Role(1L, "USER"));
    }

    public Account build(){
        var account = new Account();
        account.setId(id);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setPassword(password);
        account.setEnabled(enabled);
        account.setStatus(status);
        account.setEmployee(employee);
        account.setRoles(roles);
        return account;
    }

    public AccountBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountBuilder setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public AccountBuilder setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public AccountBuilder setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public AccountBuilder setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }
}
