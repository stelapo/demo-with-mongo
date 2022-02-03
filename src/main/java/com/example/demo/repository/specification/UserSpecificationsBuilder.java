package com.example.demo.repository.specification;

import com.example.demo.model.SearchCriteria;
import com.example.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserSpecificationsBuilder {
    private final List<SearchCriteria> searchCriteriaList;

    public UserSpecificationsBuilder() {
        searchCriteriaList = new ArrayList<SearchCriteria>();
    }

    public UserSpecificationsBuilder addSearchCriteria(String key, String operation, Object value) {
        searchCriteriaList.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<User> build() {
        if (searchCriteriaList.size() == 0) {
            return null;
        }

        List<Specification> specificationList = searchCriteriaList.stream()
                .map(UserSpecification::new)
                .collect(Collectors.toList());

        Specification specification = specificationList.get(0);
        for (int i = 1; i < specificationList.size(); i++) {
            specification = Specification.where(specification).and(specificationList.get(i));
        }
        return specification;
    }
}
