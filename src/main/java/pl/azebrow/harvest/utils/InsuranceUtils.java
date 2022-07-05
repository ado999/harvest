package pl.azebrow.harvest.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.azebrow.harvest.model.Employee;
import pl.azebrow.harvest.model.Insurance;
import pl.azebrow.harvest.repository.InsuranceRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class InsuranceUtils {

    private final InsuranceRepository insuranceRepository;

    public Boolean isInsuranceValid(Employee employee){
        LocalDate now = LocalDate.now();
        Collection<Insurance> policies = insuranceRepository.findAllByEmployee(employee);
        return policies
                .stream()
                .anyMatch(p -> now.isAfter(p.getValidFrom()) && now.isBefore(p.getValidTo()));
    }

    public Integer getRemainingValidityInDays(Employee employee){
        if (!isInsuranceValid(employee)){
            return 0;
        }
        LocalDate now = LocalDate.now();
        Collection<Insurance> policies = insuranceRepository.findAllByEmployee(employee);
        return policies
                .stream()
                .filter(p -> now.isAfter(p.getValidFrom()) && now.isBefore(p.getValidTo()))
                .max(Comparator.comparing(Insurance::getValidTo))
                .map(i -> DAYS.between(now, i.getValidTo()))
                .map(Math::toIntExact)
                .orElseThrow();
    }

}
