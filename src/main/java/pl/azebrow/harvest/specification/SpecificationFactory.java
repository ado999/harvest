package pl.azebrow.harvest.specification;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationFactory<T> {

    private final String classSimpleName;

    public SpecificationFactory(Class<T> clazz){
        this.classSimpleName = clazz.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public Specification<T> getSpecification(SearchCriteria criteria){
        return switch (classSimpleName){
            case "Job" -> (Specification<T>) new JobSpecification(criteria);
            case "Payment" -> (Specification<T>) new PaymentSpecification(criteria);
            default -> null;
        };
    }

}
