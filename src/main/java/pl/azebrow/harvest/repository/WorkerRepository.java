package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
