package pl.azebrow.harvest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.azebrow.harvest.model.Location;

import java.util.Collection;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("SELECT l FROM Location l WHERE l.disabled = false OR l.disabled = ?1")
    Page<Location> findAllWithDisabledFilter(boolean showDisabled, Pageable pageable);
}
