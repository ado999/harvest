package pl.azebrow.harvest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.azebrow.harvest.model.Insurance;
import pl.azebrow.harvest.model.JobType;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {
    @Query("SELECT jt FROM JobType jt WHERE jt.disabled = false OR jt.disabled = ?1")
    Page<JobType> findAllWithDisabledFilter(boolean showDisabled, Pageable pageable);
}
