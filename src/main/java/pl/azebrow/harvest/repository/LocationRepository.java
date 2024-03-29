package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.azebrow.harvest.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
