package com.saddham.parser;

import com.google.common.base.Preconditions;
import com.saddham.model.Cron;
import com.saddham.model.ICron;
import com.saddham.model.definition.CronDefinition;
import com.saddham.model.field.CronField;
import com.saddham.model.field.definition.FieldDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser for cron expressions.
 */
public class CronParser {

    /**
     * Field parsers in sorted in order
     */
    private final List<CronParserField> fieldParsers;
    private final CronDefinition cronDefinition;

    /**
     * @param cronDefinition - cronDefinition of cron expressions to be parsed if null, a NullPointerException will be raised.
     */
    public CronParser(final CronDefinition cronDefinition) {
        this.cronDefinition = Preconditions.checkNotNull(cronDefinition, "CronDefinition must not be null");

        this.fieldParsers = cronDefinition.getFieldDefinitions().stream()
                .map(this::toCronParserField)
                .sorted(CronParserField.createFieldTypeComparator())
                .collect(Collectors.toList());
    }

    private CronParserField toCronParserField(final FieldDefinition fieldDefinition) {
        return new CronParserField(fieldDefinition.getFieldName(), fieldDefinition.getConstraints());
    }

    /**
     * Parse string with cron expression.
     *
     * @param expression - cron expression, never null
     * @return Cron instance, corresponding to cron expression received
     * @throws IllegalArgumentException if expression does not match cron definition
     */
    public ICron parse(final String expression) {
        Preconditions.checkNotNull(expression, "Expression must not be null");
        final String replaced = expression.replaceAll("\\s+", " ").trim();
        if (replaced.isEmpty()) {
            throw new IllegalArgumentException("Empty expression!");
        }

        final String[] expressionParts = replaced.toUpperCase().split(" ");
        final int expressionLength = expressionParts.length;

        if (fieldParsers.size() != expressionLength) {
            throw new IllegalArgumentException(
                    String.format("Cron expression contains %s parts but we expect %s", expressionLength, fieldParsers.size()));
        }

        try {
            final int size = expressionParts.length;
            final List<CronField> results = new ArrayList<>(size + 1);
            for (int j = 0; j < size; j++) {
                results.add(fieldParsers.get(j).parse(expressionParts[j]));
            }
            return new Cron(cronDefinition, results).validate();
        } catch (final IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Failed to parse cron expression. %s", e.getMessage()), e);
        }
    }
}

