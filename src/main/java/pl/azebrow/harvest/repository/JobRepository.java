package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.azebrow.harvest.model.Job;

@Repository
public interface JobRepository
        extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
}
