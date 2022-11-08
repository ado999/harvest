package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.enums.RoleEnum;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.request.LocationUpdateRequest;
import pl.azebrow.harvest.response.LocationResponse;
import pl.azebrow.harvest.service.LocationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/location")
@Secured({RoleEnum.Constants.ADMIN, RoleEnum.Constants.STAFF})
@RequiredArgsConstructor
@Validated
public class LocationController {

    private final LocationService locationService;

    private final ModelMapper mapper;

    @GetMapping
    public Page<LocationResponse> getLocations(
            @RequestParam(required = false, defaultValue = "false") boolean showDisabled,
            Pageable pageable
    ) {
        var locations = locationService.getLocations(showDisabled, pageable);
        return locations
                .map(l -> mapper.map(l, LocationResponse.class));
    }

    @Secured(RoleEnum.Constants.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createLocation(
            @Valid @RequestBody LocationRequest locationRequest
    ) {
        locationService.createLocation(locationRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateLocation(
            @NotNull @Min(1) @PathVariable Long id,
            @Valid @RequestBody LocationUpdateRequest updateRequest
    ) {
        locationService.updateLocation(id, updateRequest);
    }

}
