package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PasswordChangeRequest {
    @Size(min = 8)
    private String password;
}
