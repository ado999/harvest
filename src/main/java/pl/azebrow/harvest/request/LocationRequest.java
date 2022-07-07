package pl.azebrow.harvest.request;

import lombok.Data;
import pl.azebrow.harvest.model.User;

@Data
public class LocationRequest {

    private Long ownerId;
    private String description;

}
