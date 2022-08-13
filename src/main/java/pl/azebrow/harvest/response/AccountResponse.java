package pl.azebrow.harvest.response;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
}
