package com.saddham.model;

import com.google.common.base.Preconditions;
import com.saddham.model.definition.CronDefinition;
import com.saddham.model.field.CronField;
import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.expression.visitor.ValidationFieldExpressionVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Cron implements ICron {
    private static final long serialVersionUID = 7487370826825439098L;
    private final CronDefinition cronDefinition;
    private final Map<CronFieldName, CronField> fields;
    private String asString;

    /**
     * Creates a Cron with the given cron definition and the given fields.
     *
     * @param cronDefinition the definition to use for this Cron
     * @param fields         the fields that should be used
     */
    public Cron(final CronDefinition cronDefinition, final List<CronField> fields) {
        this.cronDefinition = Preconditions.checkNotNull(cronDefinition, "CronDefinition must not be null");
        Preconditions.checkNotNull(fields, "CronFields cannot be null");
        this.fields = new EnumMap<>(CronFieldName.class);
        for (final CronField field : fields) {
            this.fields.put(field.getField(), field);
        }
    }

    /**
     * Retrieve all cron field values as map.
     *
     * @return unmodifiable Map with key CronFieldName and values CronField, never null
     */
    public Map<CronFieldName, CronField> retrieveFieldsAsMap() {
        return Collections.unmodifiableMap(fields);
    }

    public String asString() {
        if (asString == null) {
            final ArrayList<CronField> temporaryFields = new ArrayList<>(fields.values());
            temporaryFields.sort(CronField.createFieldComparator());
            final StringBuilder builder = new StringBuilder();
            for (final CronField field : temporaryFields) {
                builder.append(String.format("%s ", field.getExpression().asString()));
            }
            asString = builder.toString().trim();
        }
        return asString;
    }

    public CronDefinition getCronDefinition() {
        return cronDefinition;
    }

    /**
     * Validates this Cron instance by validating its cron expression.
     *
     * @return this Cron instance
     * @throws IllegalArgumentException if the cron expression is invalid
     */
    public ICron validate() {
        for (final Map.Entry<CronFieldName, CronField> field : retrieveFieldsAsMap().entrySet()) {
            final CronFieldName fieldName = field.getKey();
            field.getValue().getExpression().accept(
                    new ValidationFieldExpressionVisitor(getCronDefinition().getFieldDefinition(fieldName).getConstraints())
            );
        }

        return this;
    }
}
