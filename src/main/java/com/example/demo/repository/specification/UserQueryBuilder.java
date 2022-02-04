package com.example.demo.repository.specification;

import com.example.demo.model.SearchCriteria;
import com.example.demo.model.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserQueryBuilder {
    private final List<SearchCriteria> searchCriteriaList;

    public UserQueryBuilder() {
        searchCriteriaList = new ArrayList<SearchCriteria>();
    }

    public UserQueryBuilder addSearchCriteria(String key, String operation, Object value) {
        searchCriteriaList.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Query build() {
        if (searchCriteriaList.size() == 0) {
            return null;
        }

        List<UserCriteriaQuery> specificationList = searchCriteriaList.stream()
                .map(UserCriteriaQuery::new)
                .collect(Collectors.toList());

        Query query = new Query();
        //UserCriteriaQuery specification = specificationList.get(0);
        for (int i = 0; i < specificationList.size(); i++) {
            //specification = Specification.where(specification).and(specificationList.get(i));
            query.addCriteria(specificationList.get(i));
        }
        return query;
    }
}
