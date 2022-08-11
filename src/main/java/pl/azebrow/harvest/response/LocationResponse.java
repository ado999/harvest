package pl.azebrow.harvest.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocationResponse {

    private Long id;
    private AccountResponse owner;
    private String description;
    private LocalDateTime creationDate;

}
