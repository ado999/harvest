package pl.azebrow.harvest.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import pl.azebrow.harvest.model.Job;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

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
        return cb.lessThanOrEqualTo(
                root.get("date"),
                LocalDateTime.parse((CharSequence) criteria.getValue())
        );
    }

    private Predicate dateToPredicate(Root<Job> root, CriteriaQuery<?> q, CriteriaBuilder cb) {
        return cb.greaterThanOrEqualTo(
                root.get("date"),
                LocalDateTime.parse((CharSequence) criteria.getValue())
        );
    }

}
