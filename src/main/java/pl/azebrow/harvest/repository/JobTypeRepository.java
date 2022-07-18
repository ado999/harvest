package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.azebrow.harvest.model.JobType;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {
}
