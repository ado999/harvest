package pl.azebrow.harvest.integration.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import pl.azebrow.harvest.integration.BaseIntegrationTest;
import pl.azebrow.harvest.repository.LocationRepository;
import pl.azebrow.harvest.request.LocationUpdateRequest;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(scripts = "classpath:db/location_it.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class LocationIntegrationTest extends BaseIntegrationTest {

    private final static String LOCATION_URL = "/api/v1/location";

    @Autowired
    private LocationRepository locationRepository;

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAvailableLocations() throws Exception {
        mockMvc.perform(get(LOCATION_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldReturnAllLocations() throws Exception {
        mockMvc.perform(get(LOCATION_URL)
                        .param("showDisabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(5)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateLocation() throws Exception {
        var locationRequest = new LocationRequestBuilder()
                .owner(1L)
                .description("New location")
                .disabled(false)
                .locationRequest();
        mockMvc.perform(post(LOCATION_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(locationRequest)))
                .andExpect(status().isCreated());
        var location = locationRepository.findById(6L);
        assertTrue(location.isPresent());
        assertEquals(locationRequest.getOwner(), location.get().getOwner().getId());
        assertEquals(locationRequest.getDescription(), location.get().getDescription());
        assertEquals(locationRequest.getDisabled(), location.get().getDisabled());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    public void shouldUpdateLocation() throws Exception {
        var locationUpdateRequest = new LocationUpdateRequest();
        locationUpdateRequest.setDisabled(true);
        mockMvc.perform(put(LOCATION_URL + "/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUtils.stringify(locationUpdateRequest)))
                .andExpect(status().isNoContent());
        var location = locationRepository.findById(1L);
        assertTrue(location.isPresent());
        assertEquals(locationUpdateRequest.getDisabled(), location.get().getDisabled());
    }

}
