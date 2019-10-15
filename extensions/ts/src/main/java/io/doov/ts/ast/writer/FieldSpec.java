/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.ts.ast.writer;

import java.time.LocalDate;
import java.util.Objects;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.DslField;

public class FieldSpec {

    private final String name;
    private final DslField<?> field;
    private final FieldInfo fieldInfo;

    public FieldSpec(String name, DslField<?> field, FieldInfo fieldInfo) {
        this.name = name;
        this.field = field;
        this.fieldInfo = fieldInfo;
    }

    public String name() {
        return name;
    }

    public DslField<?> field() {
        return field;
    }

    public FieldInfo fieldInfo() {
        return fieldInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FieldSpec fieldSpec = (FieldSpec) o;
        return name.equals(fieldSpec.name) &&
                field.id().code().equals(fieldSpec.field.id().code());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, field.id().code());
    }

    public String toConstDeclaration() {
        return "const " + name + " = DOOV." + functionTypeString() + "(DOOV.field<" + fieldTypeString() + ">('" + name + "'));";
    }

    public String functionTypeString() {
        if (fieldInfo.type().isEnum()) {
            return "f";
        } else if (Iterable.class.isAssignableFrom(fieldInfo.type())) {
            return "iterable";
        } else if (fieldInfo.type().equals(String.class)) {
            return "string";
        } else if (Number.class.isAssignableFrom(fieldInfo.type())) {
            return "number";
        } else if (fieldInfo.type().equals(Boolean.class)) {
            return "boolean";
        } else if (fieldInfo.type().equals(LocalDate.class)) {
            return "date";
        } else {
            return "f";
        }
    }

    public String fieldTypeString() {
        if (Iterable.class.isAssignableFrom(fieldInfo.type())) {
            if(fieldInfo.genericTypes().length > 0) {
                return fieldType(fieldInfo.genericTypes()[0]) + "[]";
            } else {
                return "unknown[]";
            }
        } else {
            return fieldType(fieldInfo.type());
        }
    }

    public String fieldType(Class<?> type) {
        if (type == null) {
            return "unknown";
        }
        if (type.isEnum()) {
            return type.getSimpleName();
        } else if (type.equals(String.class)) {
            return "string";
        } else if (Number.class.isAssignableFrom(type)) {
            return "number";
        } else if (type.equals(Boolean.class)) {
            return "boolean";
        } else if (type.equals(LocalDate.class)) {
            return "Date";
        } else {
            return "unknown";
        }
    }
}