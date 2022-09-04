package pl.azebrow.harvest.specification;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationFactory {

    @SuppressWarnings("unchecked")
    public <T> Specification<T> toSpecification(SearchCriteria criteria, Class<T> clazz) {
        return (Specification<T>) switch (clazz.getSimpleName()) {
            case "Job" -> new JobSpecification(criteria);
            case "Payment" -> new PaymentSpecification(criteria);
            default -> null;
        };
    }
}
