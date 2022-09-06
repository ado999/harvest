package pl.azebrow.harvest.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder {

    public static final String EMPLOYEE = "employee";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";

    private final List<SearchCriteria> criteriaList;
    private final SpecificationFactory specFactory;

    public SpecificationBuilder() {
        criteriaList = new ArrayList<>();
        specFactory = new SpecificationFactory();
    }

    public SpecificationBuilder with(String key, Object value) {
        criteriaList.add(new SearchCriteria(key, value));
        return this;
    }

    public <T> Specification<T> build(Class<T> clazz) {
        if (criteriaList.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = criteriaList
                .stream()
                .map(c -> specFactory.toSpecification(c, clazz))
                .toList();

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification
                    .where(result)
                    .and(specs.get(i));
        }

        return result;
    }

}
