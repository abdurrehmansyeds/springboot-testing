package com.abdur.testing.repository;

import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.UserInfo_;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class UserInfoSpecificationRepo {
    private final UserInfoRepository userInfoRepository;


    public List<UserInfo> findAllUserNameLike(String name) {
        return userInfoRepository.findAll(nameStartsWith(name));
    }

    public List<UserInfo> findUserNameAndPhoneLike(String name, Long phone) {
        return userInfoRepository.findAll(where(nameStartsWith(name)).and(phoneStartsWith(phone)));
    }

    public List<UserInfo> findUserByDynamicFilter(List<Filter> filters) {
        if (filters.size() > 0) {
            return userInfoRepository.findAll(getSpecificationFromFilters(filters));
        } else
            return userInfoRepository.findAll();
    }

    private Specification<UserInfo> nameStartsWith(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(UserInfo_.FIRST_NAME), "%" + name + "%");
    }

    private Specification<UserInfo> phoneStartsWith(Long phone) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get(UserInfo_.PHONE).as(String.class), "%" + phone + "%");
    }

    private Specification<UserInfo> getSpecificationFromFilters(List<Filter> filter) {
        Specification<UserInfo> specification = where(createSpecification(filter.remove(0)));
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    private Specification<UserInfo> createSpecification(Filter input) {
        switch (input.getQueryOperator()) {
            case EQUALS:
                return (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()).as(String.class), "%" + input.getValue() + "%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                                .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            default:
                throw new RuntimeException("Operation not supported yet");


        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }


}
