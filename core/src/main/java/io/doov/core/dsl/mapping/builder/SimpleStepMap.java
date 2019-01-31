package io.doov.core.dsl.mapping.builder;

import io.doov.core.dsl.DslField;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.mapping.*;

/**
 * First step for creating mapping rule.
 * Associates the in field with type {@link I} with a converter or the out field with type {@link I}
 *
 * @param <I> in type
 */
public interface SimpleStepMap<I> {

    MappingInput<I> input();

    //public SimpleStepMap(MappingInput<I> input) {
    //    this.input = input;
    //}

    //public SimpleStepMap(DslField<I> inFieldInfo) {
    //    this(new FieldInput<>(inFieldInfo));
    //}

    /**
     * Return the step mapping
     *
     * @param <O>           out type
     * @param typeConverter type converter
     * @return the step mapping
     */
    default <O> SimpleStepMap<O> using(TypeConverter<I, O> typeConverter) {
        return () -> new ConverterInput<>(this.input(),typeConverter);
    }

    /**
     * Return the mapping rule
     *
     * @param output consumer output
     * @return the mapping rule
     */
    default DefaultMappingRule<I> to(MappingOutput<I> output) {
        return new DefaultMappingRule<>(input(), output);
    }

    /**
     * Return the mapping rule
     *
     * @param outFieldInfo out field info
     * @return the mapping rule
     */
    default DefaultMappingRule<I> to(DslField<I> outFieldInfo) {
        return this.to(new FieldOutput<>(outFieldInfo));
    }

    /**
     * Return the mapping rule
     *
     * @param consumer out field info
     * @return the mapping rule
     */
    default DefaultMappingRule<I> to(TriConsumer<DslModel, Context, I> consumer) {
        return this.to(new ConsumerOutput<>(consumer));
    }
}
