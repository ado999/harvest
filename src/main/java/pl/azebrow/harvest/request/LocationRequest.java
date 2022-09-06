package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LocationRequest {
    @NotNull
    @Min(1)
    private Long owner;

    @NotNull
    @Size(min = 2)
    private String description;

    @NotNull
    private Boolean disabled;
}
