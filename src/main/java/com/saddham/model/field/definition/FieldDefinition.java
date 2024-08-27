package com.saddham.model.field.definition;

import com.google.common.base.Preconditions;
import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.constraint.FieldConstraints;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class FieldDefinition implements Serializable {

    private static final long serialVersionUID = 7285200909397193383L;
    private final CronFieldName fieldName;
    private final FieldConstraints constraints;

    public FieldDefinition(final CronFieldName fieldName, final FieldConstraints constraints) {
        this.fieldName = Preconditions.checkNotNull(fieldName, "CronFieldName must not be null");
        this.constraints = Preconditions.checkNotNull(constraints, "FieldConstraints must not be null");
    }

    public CronFieldName getFieldName() {
        return fieldName;
    }

    public FieldConstraints getConstraints() {
        return constraints;
    }

    public static Comparator<FieldDefinition> createFieldDefinitionComparator() {
        return Comparator.comparingInt(o -> o.getFieldName().getOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDefinition that = (FieldDefinition) o;
        return fieldName == that.fieldName && constraints.equals(that.constraints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, constraints);
    }
}

