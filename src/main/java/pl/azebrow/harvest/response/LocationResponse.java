package pl.azebrow.harvest.response;

import pl.azebrow.harvest.model.User;

import java.time.LocalDateTime;

public class LocationResponse {

    private Long id;
    private User owner;
    private String description;
    private LocalDateTime creationDate;

}
