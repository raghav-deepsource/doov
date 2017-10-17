/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.UnaryMetadata.NOT;

import java.util.function.Predicate;

import io.doov.core.FieldModel;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.UnaryMetadata;

public class LogicalUnaryCondition extends AbstractStepCondition {

    private LogicalUnaryCondition(UnaryMetadata metadata, Predicate<FieldModel> predicate) {
        super(metadata, predicate);
    }

    public static LogicalUnaryCondition negate(StepCondition value) {
        return new LogicalUnaryCondition(new UnaryMetadata(NOT, value),
                        fieldContext -> value.predicate().negate().test(fieldContext));
    }

}