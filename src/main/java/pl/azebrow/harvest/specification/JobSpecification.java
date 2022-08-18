package pl.azebrow.harvest.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.azebrow.harvest.model.Job;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
public class JobSpecification implements Specification<Job> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Job> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return switch (criteria.getKey()) {
            case "location", "employee", "jobType", "approver" -> idPredicate(root, q, cb);
            case "dateFrom" -> dateFromPredicate(root, q, cb);
            case "dateTo" -> dateToPredicate(root, q, cb);
            default -> null;
        };
    }

    private Predicate idPredicate(Root<Job> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return cb.equal(
                root.get(criteria.getKey()).<Long>get("id"),
                criteria.getValue()
        );
    }

    private Predicate dateFromPredicate(Root<Job> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        try {
            return cb.greaterThanOrEqualTo(
                    root.get("date"),
                    LocalDate.parse((CharSequence) criteria.getValue()).atTime(LocalTime.MIN)
            );
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Predicate dateToPredicate(Root<Job> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        try {
            return cb.lessThanOrEqualTo(
                    root.get("date"),
                    LocalDate.parse((CharSequence) criteria.getValue()).atTime(LocalTime.MAX)
            );
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
