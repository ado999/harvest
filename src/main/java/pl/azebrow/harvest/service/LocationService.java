package pl.azebrow.harvest.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.azebrow.harvest.model.Location;
import pl.azebrow.harvest.model.User;
import pl.azebrow.harvest.repository.LocationRepository;
import pl.azebrow.harvest.request.LocationRequest;
import pl.azebrow.harvest.response.LocationResponse;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserService userService;
    private final LocationRepository locationRepository;

    private final ModelMapper mapper;
    public Collection<LocationResponse> getLocations() {
        return locationRepository
                .findAll()
                .stream().map(l -> mapper.map(l, LocationResponse.class))
                .collect(Collectors.toList());
    }

    public void createLocation(LocationRequest locationRequest) {
        Location location = new Location();
        User user = userService.findUserById(locationRequest.getOwnerId());
        location.setOwner(user);
        location.setDescription(location.getDescription());
        locationRepository.save(location);
    }
}
