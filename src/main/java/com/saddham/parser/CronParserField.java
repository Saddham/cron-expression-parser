package com.saddham.parser;

import com.google.common.base.Preconditions;
import com.saddham.model.field.CronField;
import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.constraint.FieldConstraints;

import java.util.Comparator;

/**
 * Represents a cron field.
 */
public class CronParserField {

    private final CronFieldName field;
    private final FieldConstraints constraints;
    private final FieldParser parser;

    /**
     * Constructor.
     *
     * @param fieldName   - CronFieldName instance
     * @param constraints - FieldConstraints, constraints
     */
    public CronParserField(final CronFieldName fieldName, final FieldConstraints constraints) {
        field = Preconditions.checkNotNull(fieldName, "CronFieldName must not be null");
        this.constraints = Preconditions.checkNotNull(constraints, "FieldConstraints must not be null");
        parser = new FieldParser();
    }

    /**
     * Returns field name.
     *
     * @return CronFieldName, never null
     */
    public CronFieldName getField() {
        return field;
    }

    /**
     * Parses a String cron expression.
     *
     * @param expression - cron expression
     * @return parse result as CronFieldParseResult instance - never null. May throw a RuntimeException if cron expression is bad.
     */
    public CronField parse(final String expression) {
        return new CronField(field, parser.parse(expression), constraints);
    }

    /**
     * Create a Comparator that compares CronField instances using CronFieldName value.
     *
     * @return Comparator for CronField instance, never null.
     */
    public static Comparator<CronParserField> createFieldTypeComparator() {
        return Comparator.comparingInt(o -> o.getField().getOrder());
    }

    @Override
    public String toString() {
        return "CronParserField{" + "field=" + field + '}';
    }
}
