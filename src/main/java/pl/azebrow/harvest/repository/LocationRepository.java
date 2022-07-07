package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
