package pl.azebrow.harvest.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {

    private final List<SearchCriteria> criteriaList;
    private final SpecificationFactory<T> specFactory;

    public SpecificationBuilder(Class<T> clazz) {
        criteriaList = new ArrayList<>();
        specFactory = new SpecificationFactory<>(clazz);
    }

    public SpecificationBuilder<T> with(String key, Object value) {
        criteriaList.add(new SearchCriteria(key, value));
        return this;
    }

    public Specification<T> build() {
        if (criteriaList.isEmpty()) {
            return null;
        }

        List<Specification<T>> specs = criteriaList
                .stream()
                .map(specFactory::getSpecification)
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
