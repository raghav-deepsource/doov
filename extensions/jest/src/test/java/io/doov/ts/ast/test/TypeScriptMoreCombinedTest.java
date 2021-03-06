/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.ts.ast.test;

import static io.doov.assertions.ts.Assertions.assertThat;
import static io.doov.core.dsl.DOOV.matchAny;
import static io.doov.core.dsl.DOOV.sum;
import static io.doov.core.dsl.DOOV.when;
import static io.doov.ts.ast.test.JestExtension.parseAs;
import static io.doov.ts.ast.writer.DefaultImportSpec.newImport;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.AccessMode;
import java.time.LocalDate;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.doov.assertions.ts.TypeScriptAssertionContext;
import io.doov.core.dsl.field.types.*;
import io.doov.core.dsl.lang.Result;
import io.doov.core.dsl.runtime.GenericModel;
import io.doov.tsparser.TypeScriptParser;

class TypeScriptMoreCombinedTest {

    private Result result;
    private GenericModel model;
    private LocalDateFieldInfo dateField1, dateField2;
    private BooleanFieldInfo booleanField1, booleanField2;
    private IntegerFieldInfo zeroField;
    private EnumFieldInfo<AccessMode> enumField;

    private String ruleTs;

    @RegisterExtension
    static JestExtension jestExtension = new JestExtension("build/jest");

    @BeforeEach
    void beforeEach() {
        this.model = new GenericModel();
        this.enumField = model.enumField(AccessMode.READ, "enumField");
        this.dateField1 = model.localDateField(LocalDate.now(), "date 1");
        this.dateField2 = model.localDateField(LocalDate.now(), "date 2");
        this.booleanField1 = model.booleanField(false, "boolean 1");
        this.booleanField2 = model.booleanField(false, "boolean 2");
        this.zeroField = model.intField(0, "zero");
    }

    @Test
    void or_and_sum() throws IOException {
        result = when((dateField1.ageAt(dateField2).greaterOrEquals(0)
                .or(dateField2.ageAt(dateField1).greaterOrEquals(0)))
                .and(sum(zeroField, zeroField).lesserThan(0)))
                .validate().withShortCircuit(false).executeOn(model);
        ruleTs = jestExtension.toTS(result);

        TypeScriptAssertionContext script = parseAs(ruleTs, TypeScriptParser::script);

        assertFalse(result.value());
        assertThat(script).numberOfSyntaxErrors().isEqualTo(0);
        assertThat(script).identifierNamesText().containsExactly("when", "ageAt", "greaterOrEquals", "or", "ageAt",
                "greaterOrEquals", "and", "sum", "lesserThan", "validate");
        assertThat(script).identifierReferencesText().containsExactly("DOOV", "date1", "date2", "DOOV");
        assertThat(script).identifierExpressionsText().containsExactly("date2", "date1", "zero", "zero");
        assertThat(script).literalsText().containsExactly("0", "0", "0");
        assertThat(script).arrayLiteralsText().isEmpty();
    }

    @Test
    void and_and_and_match_any_and_and() throws IOException {
        result = when(enumField.eq(AccessMode.WRITE)
                .and(booleanField1.isFalse())
                .and(matchAny(booleanField1.isTrue(),
                        booleanField2.not()
                                .and(zeroField.between(0, 1))))
                .and(zeroField.eq(1)))
                .validate().withShortCircuit(false).executeOn(model);
        ruleTs = jestExtension.toTS(result);

        TypeScriptAssertionContext script = parseAs(ruleTs, TypeScriptParser::script);

        assertFalse(result.value());
        assertThat(script).numberOfSyntaxErrors().isEqualTo(0);
        assertThat(script).identifierNamesText().containsExactly("when", "eq", "WRITE", "and", "eq", "and", "matchAny", "eq", "not",
                "and", "greaterOrEquals", "and", "lesserThan", "and", "eq", "validate");
        assertThat(script).identifierReferencesText().containsExactly("DOOV", "enumField", "boolean1", "DOOV", "boolean1",
                "boolean2", "zero", "zero", "zero");
        assertThat(script).identifierExpressionsText().containsExactly("AccessMode");
        assertThat(script).literalsText().containsExactly("false", "true", "0", "1", "1");
        assertThat(script).arrayLiteralsText().isEmpty();
    }

    @Test
    void or_and_and_and() throws IOException {
        result = when(zeroField.isNull().or(zeroField.eq(0))
                .and(booleanField1.isFalse())
                .and(dateField1.ageAt(dateField2).lesserThan(0)
                        .and(dateField2.ageAt(dateField1).greaterOrEquals(0))))
                .validate().withShortCircuit(false).executeOn(model);
        ruleTs = jestExtension.toTS(result);

        TypeScriptAssertionContext script = parseAs(ruleTs, TypeScriptParser::script);

        assertFalse(result.value());
        assertThat(script).numberOfSyntaxErrors().isEqualTo(0);
        assertThat(script).identifierNamesText().containsExactly("when", "isNull", "or", "eq", "and", "eq", "and", "ageAt",
                "lesserThan", "and", "ageAt", "greaterOrEquals", "validate");
        assertThat(script).identifierReferencesText().containsExactly("DOOV", "zero", "zero", "boolean1", "date1", "date2");
        assertThat(script).identifierExpressionsText().containsExactly("date2", "date1");
        assertThat(script).literalsText().containsExactly("0", "false", "0", "0");
        assertThat(script).arrayLiteralsText().isEmpty();
    }
    @AfterAll
    static void tearDown() {
        jestExtension.getJestTestSpec().getImports().add(newImport("doov", "BooleanFunction"));
        jestExtension.getJestTestSpec().getTestStates().add("enum AccessMode { READ, WRITE, EXECUTE };");
        LocalDate now = LocalDate.now();
        jestExtension.getJestTestSpec().getBeforeEachs().add("model = {enumField: AccessMode.READ, boolean1: false, " +
                "boolean2: false, zero: 0, date1: new Date('" + now + "'), date2: new Date('" + now + "') };");
    }
}
