package pl.azebrow.harvest.request;

import lombok.Data;

@Data
public class AccountUpdateRequest {

    private String firstName;
    private String lastName;
    private Boolean enabled;

}
