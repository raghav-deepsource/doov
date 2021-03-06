/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.assertions.ts;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.assertj.core.api.*;

import io.doov.tsparser.TypeScriptParser;

public class TypeScriptAssert extends AbstractAssert<TypeScriptAssert, TypeScriptAssertionContext> {

    public TypeScriptAssert(TypeScriptAssertionContext context, Class<?> typeScriptAssertClass) {
        super(context, typeScriptAssertClass);
    }

    public IntegerAssert numberOfSyntaxErrors() {
        return new IntegerAssert(this.actual.getParser().getNumberOfSyntaxErrors());
    }

    public ListAssert<TypeScriptParser.IdentifierNameContext> identifierNames() {
        return new ListAssert<>(this.actual.getIdentifierNames());
    }

    public ListAssert<String> identifierNamesText() {
        return identifierNames().extracting(TypeScriptParser.IdentifierNameContext::getText);
    }

    public ListAssert<TypeScriptParser.IdentifierReferenceContext> identifierReferences() {
        return new ListAssert<>(this.actual.getIdentifierReferences());
    }

    public ListAssert<String> identifierReferencesText() {
        return identifierReferences().extracting(TypeScriptParser.IdentifierReferenceContext::getText);
    }

    public ListAssert<TypeScriptParser.IdentifierExpressionContext> identifierExpressions() {
        return new ListAssert<>(this.actual.getIdentifierExpressions());
    }

    public ListAssert<String> identifierExpressionsText() {
        return identifierExpressions().extracting(TypeScriptParser.IdentifierExpressionContext::getText);
    }

    public ListAssert<TypeScriptParser.LiteralContext> literals() {
        return new ListAssert<>(this.actual.getLiterals());
    }

    public ListAssert<String> literalsText() {
        return literals().extracting(TypeScriptParser.LiteralContext::getText);
    }

    public ListAssert<TypeScriptParser.ArrayLiteralContext> arrayLiterals() {
        return new ListAssert<>(this.actual.getArrayLiterals());
    }

    public ListAssert<String> arrayLiteralsText() {
        return arrayLiterals().extracting(TypeScriptParser.ArrayLiteralContext::getText);
    }

    public ListAssert<ErrorNode> errors() {
        return new ListAssert<>(this.actual.getErrors());
    }

    public ListAssert<String> errorsText() {
        return errors().extracting(ErrorNode::getText);
    }

    public <T extends ParserRuleContext> ListAssert<T> rules(Class<T> ruleType) {
        return new ListAssert<>(this.actual.getRules(ruleType));
    }
}
