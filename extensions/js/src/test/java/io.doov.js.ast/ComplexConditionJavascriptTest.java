package io.doov.js.ast;

import static io.doov.core.dsl.DOOV.*;
import static io.doov.core.dsl.time.LocalDateSuppliers.today;
import static io.doov.core.dsl.time.TemporalAdjuster.firstDayOfYear;
import static io.doov.js.ast.ScriptEngineFactory.fieldModelToJS;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.junit.jupiter.api.*;

import io.doov.core.dsl.field.types.*;
import io.doov.core.dsl.lang.ValidationRule;
import io.doov.core.dsl.runtime.GenericModel;

public class ComplexConditionJavascriptTest {

    private ValidationRule rule;
    private static GenericModel model = new GenericModel();
    private static StringFieldInfo A = model.stringField("value", "A"),
            validEmail = model.stringField("test@test.fr", "validAccountEmail"),
            invalidEmail = model.stringField("test@test.uk", "invalidAccountEmail"),
            accountCountry = model.stringField("FR", "accountCountry"),
            accountCompany = model.stringField("LESFURETS.COM", "accountCompany"),
            BLABLACAR = model.stringField("BLABLACAR", "BLABLACAR"),
            userFirstName = model.stringField("test", "userFirstName"),
            userLastName = model.stringField("TEST", "userLastName"),
            userId = model.stringField("notnull", "userId"),
            accountPhoneNumber = model.stringField("+33123456789", "accountPhoneNumber");
    private LocalDateFieldInfo userBirthdate = model.localDateField(LocalDate.of(1980, 1, 1), "userbd"),
            accountCreationDate = model.localDateField(LocalDate.of(1999, 1, 1), " accountCreationDate");
    private IntegerFieldInfo configMaxEmailSize = model.intField(24, "maxsize"),
            ageat = model.intField(39, "ageat"),
            configurationMinAge = model.intField(18, "configMinAge"),
            timesBase = model.intField(3, "timesbase");
    private String request, result;
    private static ByteArrayOutputStream ops;
    private static ScriptEngine engine;
    private static AstJavascriptWriter writer;

    @BeforeAll
    static void init() {
        ops = new ByteArrayOutputStream();
        engine = ScriptEngineFactory.create();
        writer = new AstJavascriptWriter(ops);
    }

    @BeforeEach
    void beforeEach() throws ScriptException {
        ops.reset();
        engine.eval(fieldModelToJS(model));
    }

    @Test
    void eval_times_chaining() throws ScriptException {
        rule = when(timesBase.times(2).times(2).times(2).eq(24)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);

    }

    // From here, cdo stands for complex date operator
    @Test
    void eval_cdo_years_between() throws ScriptException {
        rule = when(today().plus(2, YEARS)
                .yearsBetween(today().plus(12, MONTHS).plus(1, YEARS))
                .eq(0)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);

    }

    @Test
    void eval_cdo_birthdateEq() throws ScriptException {
        rule = when(userBirthdate.plus(2, YEARS)
                .yearsBetween(userBirthdate.plus(12, MONTHS).plus(1, YEARS))
                .eq(0)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);

    }

    @Test
    void eval_cdo_todayEq() throws ScriptException {
        rule = when(today().plus(2, YEARS).minus(12, MONTHS).minus(1, YEARS)
                .eq(today())).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);

    }

    @Test
    void eval_cdo_value_false() throws ScriptException {
        rule = when(userBirthdate.yearsBetween(today()).eq(38)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("false", result);
    }

    // eq operator can't accept numerical fonction as parameter
    @Test
    void eval_cdo_value_true() throws ScriptException {
        rule = when(userBirthdate.yearsBetween(today()).eq(39)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_cdo_field_true() throws ScriptException {
        rule = when(userBirthdate.yearsBetween(today()).eq(ageat)).validate().withShortCircuit(false);
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_complex_request() throws ScriptException {
        rule = when(count(userFirstName.anyMatch("test", "note", "error", "name"), userLastName.matches("[A-Z]+"),
                userBirthdate.ageAt(today()).greaterOrEquals(18)).eq(3)
                .and(today().plus(2, YEARS).minus(12, MONTHS).minus(1, YEARS).eq(today())))
                .validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_email_valid() throws ScriptException {
        rule = when(validEmail.matches("\\w+[@]\\w+\\.com").or(validEmail.matches("\\w+[@]\\w+\\.fr"))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_email_invalid() throws ScriptException {
        rule = when(invalidEmail.matches("\\w+[@]\\w+\\.com").or(invalidEmail.matches("\\w+[@]\\w+\\.fr"))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("false", result);
    }

    @Test
    void eval_account_rule() throws ScriptException {
        rule = when(matchAll(userBirthdate.ageAt(today()).greaterOrEquals(18),
                validEmail.length().lesserOrEquals(configMaxEmailSize),
                accountCountry.eq("FR").and(accountPhoneNumber.startsWith("+33")))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_account_rule_second() throws ScriptException {
        rule = when(userBirthdate.ageAt(today()).greaterOrEquals(18)
                .and(validEmail.length().lesserOrEquals(configMaxEmailSize))
                .and(accountCountry.eq("FR"))
                .and(accountPhoneNumber.startsWith("+33"))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_rule_user() throws ScriptException {
        rule = when(count(userFirstName.isNotNull(),
                userLastName.isNotNull().and(userLastName.matches("[A-Z]+")))
                .greaterOrEquals(0)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_rule_user_second() throws ScriptException {
        rule = when(userLastName.isNotNull().and(userLastName.matches("[A-Z]+")
                .and(count(accountPhoneNumber.isNotNull(),
                        validEmail.isNotNull())
                        .greaterThan(0)))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_user_adult() throws ScriptException {
        rule = when(userBirthdate.ageAt(accountCreationDate).greaterOrEquals(18)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_user_adult_firstday() throws ScriptException {
        rule = when(userBirthdate.ageAt(accountCreationDate.with(firstDayOfYear())).greaterOrEquals(18)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_user_id() throws ScriptException {
        rule = when(userId.isNotNull()).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_user_age() throws ScriptException {
        rule = when(userBirthdate.ageAt(today()).greaterOrEquals(18)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_user_age_second() throws ScriptException {
        rule = when(userBirthdate.after(userBirthdate.minus(1, DAYS))).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_sum() throws ScriptException {
        rule = when(sum(configurationMinAge.times(0), configMaxEmailSize.times(1)).greaterOrEquals(0)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_min() throws ScriptException {
        rule = when(min(configurationMinAge, configMaxEmailSize).greaterOrEquals(0)).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }

    @Test
    void eval_account_not_blablacar() throws ScriptException {
        rule = when(accountCompany.eq(BLABLACAR).not()).validate();
        writer.writeRule(rule);
        request = new String(ops.toByteArray(), Charset.forName("UTF-8"));
        result = engine.eval(request).toString();
        assertEquals("true", result);
    }



    @AfterEach
    void afterEach() {
        System.out.println(request + " -> " + result + "\n");
    }
}
