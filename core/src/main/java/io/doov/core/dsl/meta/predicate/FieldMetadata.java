/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.dsl.meta.predicate;

import static io.doov.core.dsl.meta.MetadataType.FIELD_PREDICATE;

import java.util.concurrent.atomic.AtomicInteger;

import io.doov.core.dsl.DslField;
import io.doov.core.dsl.meta.LeafMetadata;
import io.doov.core.dsl.meta.MetadataType;

public class FieldMetadata<M extends FieldMetadata<M>> extends LeafMetadata<M> implements PredicateMetadata {

    private final AtomicInteger evalTrue = new AtomicInteger();
    private final AtomicInteger evalFalse = new AtomicInteger();

    private final DslField<?> field;

    public FieldMetadata(MetadataType type, DslField<?> field) {
        super(type);
        this.field = field;
    }

    @Override
    public AtomicInteger evalTrue() {
        return evalTrue;
    }

    @Override
    public AtomicInteger evalFalse() {
        return evalFalse;
    }

    public DslField<?> field() {
        return field;
    }

    // field

    public static FieldMetadata<?> fieldMetadata(DslField<?> field) {
        return new FieldMetadata<>(FIELD_PREDICATE, field).field(field);
    }

}