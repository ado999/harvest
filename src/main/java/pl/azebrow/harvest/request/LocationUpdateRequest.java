package pl.azebrow.harvest.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LocationUpdateRequest {
    @NotNull
    private Boolean disabled;
}
