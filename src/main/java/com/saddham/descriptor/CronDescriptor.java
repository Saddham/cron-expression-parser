package com.saddham.descriptor;

import com.google.common.base.Preconditions;
import com.saddham.model.ICron;
import com.saddham.model.field.CronField;
import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.definition.FieldDefinition;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CronDescriptor {

    public CronDescriptor() {
    }

    public String describe(final ICron cron) {
        Preconditions.checkNotNull(cron, "Cron must not be null");

        final Map<CronFieldName, CronField> expressions = cron.retrieveFieldsAsMap();
        final Map<CronFieldName, FieldDefinition> fieldDefinitions = cron.getCronDefinition().retrieveFieldDefinitionsAsMap();

        return new StringBuilder()
                .append(describeExpression(fieldDefinitions.get(CronFieldName.MINUTE), expressions.get(CronFieldName.MINUTE))).append("\n")
                .append(describeExpression(fieldDefinitions.get(CronFieldName.HOUR), expressions.get(CronFieldName.HOUR))).append("\n")
                .append(describeExpression(fieldDefinitions.get(CronFieldName.DAY_OF_MONTH), expressions.get(CronFieldName.DAY_OF_MONTH))).append("\n")
                .append(describeExpression(fieldDefinitions.get(CronFieldName.MONTH), expressions.get(CronFieldName.MONTH))).append("\n")
                .append(describeExpression(fieldDefinitions.get(CronFieldName.DAY_OF_WEEK), expressions.get(CronFieldName.DAY_OF_WEEK)))
                .toString();
    }

    private String describeExpression(FieldDefinition fieldDefinition, CronField cronField) {
        OccurrenceDescriptionStrategy occurrenceDescriptionStrategy = new OccurrenceDescriptionStrategy(fieldDefinition);
        Set<Integer> occurrences = occurrenceDescriptionStrategy.describe(cronField.getExpression());

        String occurrencesStr = occurrences.stream()
                .map(String::valueOf) // Convert each Integer to a String
                .collect(Collectors.joining(" "));

        return String.format("%-14s %s", cronField.getField().getName(), occurrencesStr);
    }


}
