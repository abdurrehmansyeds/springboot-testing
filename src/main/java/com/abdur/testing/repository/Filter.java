package com.abdur.testing.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Filter {
    private String field;
    private String value;
    private QueryOperator queryOperator;
    private List<String> values;

    enum QueryOperator {
        GREATER_THAN,
        LESS_THAN,
        EQUALS,
        LIKE,
        NOT_EQ,
        IN
    }
}
