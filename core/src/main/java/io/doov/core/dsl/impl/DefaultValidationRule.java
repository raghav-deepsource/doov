/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.core.dsl.impl;

import io.doov.core.FieldModel;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.RuleMetadata;

public class DefaultValidationRule extends AbstractDSLBuilder implements ValidationRule {

    private final Metadata metadata;
    private final StepWhen stepWhen;
    private final boolean shortCircuit;

    public DefaultValidationRule(StepWhen stepWhen) {
        this(stepWhen, RuleMetadata.rule(stepWhen.metadata()), true);
    }

    public DefaultValidationRule(StepWhen stepWhen, boolean shortCircuit) {
        this(stepWhen, RuleMetadata.rule(stepWhen.metadata()), shortCircuit);
    }

    public DefaultValidationRule(StepWhen stepWhen, Metadata metadata, boolean shortCircuit) {
        this.metadata = metadata;
        this.stepWhen = stepWhen;
        this.shortCircuit = shortCircuit;
    }

    protected boolean isShortCircuit() {
        return shortCircuit;
    }

    @Override
    public StepWhen getStepWhen() {
        return stepWhen;
    }

    @Override
    public ValidationRule withShortCircuit(boolean shortCircuit) {
        return new DefaultValidationRule(stepWhen, shortCircuit);
    }

    @Override
    public Result executeOn(FieldModel model) {
        return executeOn(model, new DefaultContext(shortCircuit, stepWhen.stepCondition().metadata()));
    }

    @Override
    public Result executeOn(FieldModel model, Context context) {
        context.beforeValidate(this);
        try {
            return new DefaultResult(stepWhen.stepCondition().predicate().test(model, context), context);
        } catch (Throwable t) {
            throw new RuntimeException(readable(), t);
        } finally {
            context.afterValidate(this);
        }
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }

}
