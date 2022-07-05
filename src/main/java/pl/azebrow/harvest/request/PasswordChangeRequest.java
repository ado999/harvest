package pl.azebrow.harvest.request;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String email;
    private String password;
}
