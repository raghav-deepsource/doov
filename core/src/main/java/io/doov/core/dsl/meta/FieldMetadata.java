/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.meta;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.field.*;

public class FieldMetadata extends AbstractMetadata {

    private static final FieldMetadata EMPTY = new FieldMetadata(null, null, null);

    private final String field;
    private final String operator;
    private final String value;

    private FieldMetadata(Object field, String operator, Object value) {
        this.field = field == null ? null : field.toString();
        this.operator = operator;
        this.value = value == null ? null : value.toString();
    }

    private FieldMetadata(Readable field, String operator, Object value) {
        this.field = field == null ? null : field.readable();
        this.operator = operator;
        this.value = value == null ? null : value.toString();
    }

    private FieldMetadata(Readable field, String operator, Readable value) {
        this.field = field == null ? null : field.readable();
        this.operator = operator;
        this.value = value == null ? null : value.readable();
    }

    public static FieldMetadata emptyMetadata() {
        return EMPTY;
    }

    public static FieldMetadata equalsMetadata(FieldInfo field, Object value) {
        return new FieldMetadata(field, "equals", value);
    }

    public static FieldMetadata notEqualsMetadata(FieldInfo field, Object value) {
        return new FieldMetadata(field, "not equals", value);
    }

    public static FieldMetadata nullMetadata(FieldInfo field, Object value) {
        return new FieldMetadata(field, "is null", value);
    }

    public static FieldMetadata notNullMetadata(FieldInfo field, Object value) {
        return new FieldMetadata(field, "is not null", value);
    }

    public static FieldMetadata afterMetadata(LocalDateFieldInfo field, LocalDate value) {
        return new FieldMetadata(field, "after", value);
    }

    public static FieldMetadata beforeMetadata(LocalDateFieldInfo field, LocalDate value) {
        return new FieldMetadata(field, "before", value);
    }

    public static FieldMetadata matchesMetadata(StringFieldInfo field, String value) {
        return new FieldMetadata(field, "matches", value);
    }

    public static FieldMetadata containsMetadata(StringFieldInfo field, String value) {
        return new FieldMetadata(field, "contains", value);
    }

    public static FieldMetadata startsWithMetadata(StringFieldInfo field, String value) {
        return new FieldMetadata(field, "starts with", value);
    }

    public static FieldMetadata endsWithMetadata(StringFieldInfo field, String value) {
        return new FieldMetadata(field, "ends with", value);
    }

    public static FieldMetadata isMetadata(BooleanFieldInfo field, boolean value) {
        return new FieldMetadata(field, "is", value);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata lesserThanMetadata(
                    F field, N value) {
        return new FieldMetadata(field, "lesser than", value);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata lesserThanMetadata(
                    F field1, F field2) {
        return new FieldMetadata(field1, "lesser than", field2);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata lesserOrEqualsMetadata(
                    F field, N value) {
        return new FieldMetadata(field, "lesser or equals", value);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata lesserOrEqualsMetadata(
                    F field1, F field2) {
        return new FieldMetadata(field1, "lesser or equals", field2);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata greaterThanMetadata(
                    F field, N value) {
        return new FieldMetadata(field, "greater than", value);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata greaterThanMetadata(
                    F field1, F field2) {
        return new FieldMetadata(field1, "greater than", field2);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata greaterOrEqualsMetadata(
                    F field, N value) {
        return new FieldMetadata(field, "greater or equals", value);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata greaterOrEqualsMetadata(
                    F field1, F field2) {
        return new FieldMetadata(field1, "greater or equals", field2);
    }

    public static <F extends DefaultFieldInfo<N>, N extends Number> FieldMetadata betweenMetadata(
                    F field, Number min, N max) {
        return new FieldMetadata(field, "between", min + " and " + max);
    }

    public static FieldMetadata lengthIsMetadata(StringFieldInfo field) {
        return new FieldMetadata(field, "length is", null);
    }

    public FieldMetadata merge(FieldMetadata metadata) {
        if (this.equals(EMPTY)) {
            return metadata;
        }
        return new FieldMetadata(this.field, this.operator + " " + metadata.operator, metadata.value);
    }

    @Override
    public String readable() {
        return Stream.of("'" + field + "'", operator, value)
                        .filter(Objects::nonNull)
                        .map(Objects::toString)
                        .collect(joining(" "));
    }

}