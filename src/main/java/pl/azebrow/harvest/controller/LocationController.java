package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.constant.RoleEnum;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.request.LocationUpdateRequest;
import pl.azebrow.harvest.response.LocationResponse;
import pl.azebrow.harvest.service.LocationService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/location")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    private final ModelMapper mapper;

    @GetMapping
    public Collection<LocationResponse> getLocations(
            @RequestParam(required = false, defaultValue = "false") boolean showDisabled
    ) {
        var locations = locationService.getLocations(showDisabled);
        return locations
                .stream()
                .map(l -> mapper.map(l, LocationResponse.class))
                .collect(Collectors.toList());
    }

    @Secured(RoleEnum.Constants.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createLocation(
            @RequestBody LocationRequest locationRequest
    ) {
        locationService.createLocation(locationRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateLocation(
            @PathVariable Long id,
            @RequestBody LocationUpdateRequest updateRequest
    ) {
        locationService.updateLocation(id, updateRequest);
    }

}
