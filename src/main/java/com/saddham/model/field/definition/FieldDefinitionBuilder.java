package com.saddham.model.field.definition;

import com.google.common.base.Preconditions;
import com.saddham.model.definition.CronDefinitionBuilder;
import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.constraint.FieldConstraintsBuilder;

public class FieldDefinitionBuilder {
    protected CronDefinitionBuilder cronDefinitionBuilder;
    protected final CronFieldName fieldName;
    protected FieldConstraintsBuilder constraints;

    public FieldDefinitionBuilder(final CronDefinitionBuilder cronDefinitionBuilder, final CronFieldName fieldName) {
        this.cronDefinitionBuilder = Preconditions.checkNotNull(cronDefinitionBuilder, "ParserBuilder must not be null");
        this.fieldName = Preconditions.checkNotNull(fieldName, "CronFieldName must not be null");
        constraints = FieldConstraintsBuilder.instance().forField(fieldName);
    }

    public FieldDefinitionBuilder withValidRange(final int startRange, final int endRange) {
        constraints.withValidRange(startRange, endRange);
        return this;
    }

    public CronDefinitionBuilder and() {
        cronDefinitionBuilder.register(new FieldDefinition(fieldName, constraints.createConstraintsInstance()));
        return cronDefinitionBuilder;
    }
}

