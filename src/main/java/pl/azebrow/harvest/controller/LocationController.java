package pl.azebrow.harvest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.response.LocationResponse;
import pl.azebrow.harvest.service.LocationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public Collection<LocationResponse> getLocations(){
        return locationService.getLocations();
    }

    @PostMapping
    public void createLocation(
            @RequestBody LocationRequest locationRequest
    ){
        locationService.createLocation(locationRequest);
    }

}
