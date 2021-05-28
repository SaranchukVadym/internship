package com.epam.internship.dao.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRsqlSpecification<T> implements Specification<T> {

    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(final String property, final ComparisonOperator operator, final List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        Path<String> propertyExpression = parseProperty(root);
        List<Object> args = castArguments(propertyExpression);
        Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {
            case EQUAL:
                if (argument instanceof String) {
                    return builder.like(propertyExpression, argument.toString().replace('*', '%'));
                } else if (argument instanceof LocalDate) {
                    return builder.equal(propertyExpression, argument);
                } else if (argument instanceof LocalDateTime) {
                    Expression<LocalDateTime> expr = root.get(this.property);
                    Predicate dateFrom = builder.greaterThanOrEqualTo(expr, (LocalDateTime) argument);
                    Predicate dateEnd = builder.lessThan(expr, ((LocalDateTime) argument).plusDays(1));
                    return builder.and(dateFrom, dateEnd);
                } else if (argument == null) {
                    return builder.isNull(propertyExpression);
                } else {
                    return builder.equal(propertyExpression, argument);
                }

            case NOT_EQUAL:
                if (argument instanceof String) {
                    return builder.notLike(propertyExpression, argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return builder.isNotNull(propertyExpression);
                } else {
                    return builder.notEqual(propertyExpression, argument);
                }

            case GREATER_THAN:
                if (argument instanceof LocalDate) {
                    Expression<LocalDate> expr = root.get(this.property);
                    return builder.greaterThan(expr, (LocalDate) argument);
                } else if (argument instanceof LocalDateTime) {
                    Expression<LocalDateTime> expr = root.get(this.property);
                    return builder.greaterThan(expr, ((LocalDateTime) argument).plusDays(1));
                } else {
                    return builder.greaterThan(propertyExpression, argument.toString());
                }

            case GREATER_THAN_OR_EQUAL:
                if (argument instanceof LocalDate) {
                    Expression<LocalDate> expr = root.get(this.property);
                    return builder.greaterThanOrEqualTo(expr, (LocalDate) argument);
                } else if (argument instanceof LocalDateTime) {
                    Expression<LocalDateTime> expr = root.get(this.property);
                    return builder.greaterThanOrEqualTo(expr, (LocalDateTime) argument);
                } else {
                    return builder.greaterThanOrEqualTo(propertyExpression, argument.toString());
                }

            case LESS_THAN:
                if (argument instanceof LocalDate) {
                    Expression<LocalDate> expr = root.get(this.property);
                    return builder.lessThan(expr, (LocalDate) argument);
                } else if (argument instanceof LocalDateTime) {
                    Expression<LocalDateTime> expr = root.get(this.property);
                    return builder.lessThan(expr, (LocalDateTime) argument);
                } else {
                    return builder.lessThan(propertyExpression, argument.toString());
                }

            case LESS_THAN_OR_EQUAL:
                if (argument instanceof LocalDate) {
                    Expression<LocalDate> expr = root.get(this.property);
                    return builder.lessThanOrEqualTo(expr, (LocalDate) argument);
                } else if (argument instanceof LocalDateTime) {
                    Expression<LocalDateTime> expr = root.get(this.property);
                    return builder.lessThanOrEqualTo(expr, ((LocalDateTime) argument)
                            .plusHours(23)
                            .plusMinutes(59)
                            .plusSeconds(59)
                    );
                } else {
                    return builder.lessThanOrEqualTo(propertyExpression, argument.toString());
                }

            case IN:
                return propertyExpression.in(args);
            case NOT_IN:
                return builder.not(propertyExpression.in(args));
        }

        return null;
    }

    private Path<String> parseProperty(Root<T> root) {
        Path<String> path;
        if (property.contains(".")) {
            // Nested properties
            String[] pathSteps = property.split("\\.");
            String step = pathSteps[0];
            path = root.get(step);

            for (int i = 1; i <= pathSteps.length - 1; i++) {
                path = path.get(pathSteps[i]);
            }
        } else {
            path = root.get(property);
        }
        return path;
    }

    private List<Object> castArguments(Path<?> propertyExpression) {
        Class<?> type = propertyExpression.getJavaType();

        return arguments.stream().map(arg -> {
            if (type.equals(Integer.class)) {
                return Integer.parseInt(arg);
            } else if (type.equals(Long.class)) {
                return Long.parseLong(arg);
            } else if (type.equals(Byte.class)) {
                return Byte.parseByte(arg);
            } else if (type.isEnum()) {
                return Enum.valueOf((Class<Enum>) type, arg);
            } else if (type.equals(LocalDate.class)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(arg, formatter);
            } else if (type.equals(LocalDateTime.class)) {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd[ [HH][:mm][:ss][.SSS]]")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
                return LocalDateTime.parse(arg, formatter);
            } else {
                return arg;
            }
        }).collect(Collectors.toList());
    }

}