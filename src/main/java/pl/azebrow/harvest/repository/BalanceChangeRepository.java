package pl.azebrow.harvest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.azebrow.harvest.model.BalanceChange;

public interface BalanceChangeRepository extends JpaRepository<BalanceChange, Long> {
}
