package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AccountEmailUpdateRequest {
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
}
