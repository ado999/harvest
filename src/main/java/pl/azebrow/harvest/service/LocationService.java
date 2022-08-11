package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.Location;
import pl.azebrow.harvest.repository.LocationRepository;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.response.LocationResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final AccountService accountService;
    private final LocationRepository locationRepository;

    private final ModelMapper mapper;

    public Collection<LocationResponse> getLocations() {
        return locationRepository
                .findAll()
                .stream().map(l -> mapper.map(l, LocationResponse.class))
                .collect(Collectors.toList());
    }

    public void createLocation(LocationRequest locationRequest) {
        Account account = accountService.findUserById(locationRequest.getOwner());
        Location location = Location.builder()
                .owner(account)
                .description(locationRequest.getDescription())
                .build();
        locationRepository.save(location);
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Location with id \"%d\" not found!", id)
                );
    }
}
