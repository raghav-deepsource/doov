/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.template;

import java.util.function.BiFunction;
import java.util.function.Function;

import io.doov.core.dsl.DslField;
import io.doov.core.dsl.lang.Function4;
import io.doov.core.dsl.lang.Function5;
import io.doov.core.dsl.lang.MappingRule;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.lang.TriFunction;
import io.doov.core.dsl.mapping.MappingRegistry;

public class TemplateSpec {

    public static class Template1<T1 extends DslField<?>> {

        final TemplateParam<T1> param1;

        public Template1(TemplateParam<T1> param1) {
            this.param1 = param1;
        }

        public TemplateRule.Rule1<T1> rule(Function<T1, StepCondition> ruleFunction) {
            return new TemplateRule.Rule1<>(ruleFunction, this);
        }

        public TemplateMapping.Map1<T1> mapping(Function<T1, MappingRule> ruleFunction) {
            return new TemplateMapping.Map1<>(ruleFunction, this);
        }

        public TemplateMapping.Registry1<T1> mappings(Function<T1, MappingRegistry> registry) {
            return new TemplateMapping.Registry1<>(registry, this);
        }
    }

    public static class Template2<T1 extends DslField<?>, T2 extends DslField<?>> {

        final TemplateParam<T1> param1;
        final TemplateParam<T2> param2;

        public Template2(TemplateParam<T1> param1, TemplateParam<T2> param2) {
            this.param1 = param1;
            this.param2 = param2;
        }

        public TemplateRule.Rule2<T1, T2> rule(BiFunction<T1, T2, StepCondition> ruleFunction) {
            return new TemplateRule.Rule2<>(ruleFunction, this);
        }

        public TemplateMapping.Map2<T1, T2> mapping(BiFunction<T1, T2, MappingRule> ruleFunction) {
            return new TemplateMapping.Map2<>(ruleFunction, this);
        }

        public TemplateMapping.Registry2<T1, T2> mappings(BiFunction<T1, T2, MappingRegistry> registry) {
            return new TemplateMapping.Registry2<>(registry, this);
        }
    }

    public static class Template3<T1 extends DslField<?>, T2 extends DslField<?>, T3 extends DslField<?>> {

        final TemplateParam<T1> param1;
        final TemplateParam<T2> param2;
        final TemplateParam<T3> param3;

        public Template3(TemplateParam<T1> param1, TemplateParam<T2> param2, TemplateParam<T3> param3) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
        }

        public TemplateRule.Rule3<T1, T2, T3> rule(TriFunction<T1, T2, T3, StepCondition> ruleFunction) {
            return new TemplateRule.Rule3<>(ruleFunction, this);
        }

        public TemplateMapping.Map3<T1, T2, T3> mapping(TriFunction<T1, T2, T3, MappingRule> ruleFunction) {
            return new TemplateMapping.Map3<>(ruleFunction, this);
        }

        public TemplateMapping.Registry3<T1, T2, T3> mappings(TriFunction<T1, T2, T3, MappingRegistry> registry) {
            return new TemplateMapping.Registry3<>(registry, this);
        }
    }

    public static class Template4<T1 extends DslField<?>, T2 extends DslField<?>, T3 extends DslField<?>, T4 extends DslField<?>> {

        final TemplateParam<T1> param1;
        final TemplateParam<T2> param2;
        final TemplateParam<T3> param3;
        final TemplateParam<T4> param4;

        public Template4(TemplateParam<T1> param1, TemplateParam<T2> param2, TemplateParam<T3> param3,
                TemplateParam<T4> param4) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
            this.param4 = param4;
        }

        public TemplateRule.Rule4<T1, T2, T3, T4> rule(Function4<T1, T2, T3, T4, StepCondition> ruleFunction) {
            return new TemplateRule.Rule4<>(ruleFunction, this);
        }

        public TemplateMapping.Map4<T1, T2, T3, T4> mapping(Function4<T1, T2, T3, T4, MappingRule> ruleFunction) {
            return new TemplateMapping.Map4<>(ruleFunction, this);
        }

        public TemplateMapping.Registry4<T1, T2, T3, T4> mappings(
                Function4<T1, T2, T3, T4, MappingRegistry> registry) {
            return new TemplateMapping.Registry4<>(registry, this);
        }
    }

    public static class Template5<T1 extends DslField<?>, T2 extends DslField<?>, T3 extends DslField<?>, T4 extends DslField<?>, T5 extends DslField<?>> {

        final TemplateParam<T1> param1;
        final TemplateParam<T2> param2;
        final TemplateParam<T3> param3;
        final TemplateParam<T4> param4;
        final TemplateParam<T5> param5;

        public Template5(TemplateParam<T1> param1, TemplateParam<T2> param2, TemplateParam<T3> param3,
                TemplateParam<T4> param4, TemplateParam<T5> param5) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
            this.param4 = param4;
            this.param5 = param5;
        }

        public TemplateRule.Rule5<T1, T2, T3, T4, T5> rule(Function5<T1, T2, T3, T4, T5, StepCondition> ruleFunction) {
            return new TemplateRule.Rule5<>(ruleFunction, this);
        }

        public TemplateMapping.Map5<T1, T2, T3, T4, T5> mapping(
                Function5<T1, T2, T3, T4, T5, MappingRule> ruleFunction) {
            return new TemplateMapping.Map5<>(ruleFunction, this);
        }

        public TemplateMapping.Registry5<T1, T2, T3, T4, T5> mappings(
                Function5<T1, T2, T3, T4, T5, MappingRegistry> registry) {
            return new TemplateMapping.Registry5<>(registry, this);
        }
    }

}
