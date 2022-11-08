package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.exeption.ResourceNotFoundException;
import pl.azebrow.harvest.model.Account;
import pl.azebrow.harvest.model.Location;
import pl.azebrow.harvest.repository.LocationRepository;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.request.LocationUpdateRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final AccountService accountService;
    private final LocationRepository locationRepository;

    public Page<Location> getLocations(boolean showDisabled, Pageable pageable) {
        return locationRepository
                .findAllWithDisabledFilter(showDisabled, pageable);
    }

    public void createLocation(LocationRequest locationRequest) {
        Account account = accountService.findAccountById(locationRequest.getOwner());
        Location location = Location.builder()
                .owner(account)
                .description(locationRequest.getDescription())
                .disabled(locationRequest.getDisabled())
                .build();
        locationRepository.save(location);
    }

    public void updateLocation(Long id, LocationUpdateRequest updateRequest) {
        Location location = getLocationById(id);
        location.setDisabled(updateRequest.getDisabled());
        locationRepository.save(location);
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Location with id \"%d\" not found!", id)
                );
    }
}
