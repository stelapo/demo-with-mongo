package com.example.demo.repository.specification;

import com.example.demo.model.SearchCriteria;
import com.example.demo.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {
    private SearchCriteria criteria;

    public UserSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> user, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getValue() != null) {
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (user.get(criteria.getKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(
                            criteriaBuilder.upper(user.get(criteria.getKey())), "%" + criteria.getValue().toString().toUpperCase() + "%");
                } else { //per eventuali futuri parametri interi
                    return criteriaBuilder.equal(user.get(criteria.getKey()), criteria.getValue());
                }
            }
        }
        return criteriaBuilder.conjunction();
    }
}
