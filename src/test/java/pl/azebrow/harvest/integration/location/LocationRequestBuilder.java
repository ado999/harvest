package pl.azebrow.harvest.integration.location;

import pl.azebrow.harvest.request.LocationRequest;

public class LocationRequestBuilder {

    private Long owner;
    private String description;
    private Boolean disabled;

    public LocationRequestBuilder owner(Long owner) {
        this.owner = owner;
        return this;
    }

    public LocationRequestBuilder description(String description) {
        this.description = description;
        return this;
    }

    public LocationRequestBuilder disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public LocationRequest locationRequest() {
        var request = new LocationRequest();
        request.setOwner(owner);
        request.setDescription(description);
        request.setDisabled(disabled);
        return request;
    }

}
