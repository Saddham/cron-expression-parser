package com.saddham.model.field;

import com.google.common.base.Preconditions;
import com.saddham.model.field.constraint.FieldConstraints;
import com.saddham.model.field.expression.FieldExpression;

import java.io.Serializable;
import java.util.Comparator;

public class CronField implements Serializable {

    private static final long serialVersionUID = -4042499846855256325L;
    private final CronFieldName field;
    private final FieldExpression expression;
    private final FieldConstraints constraints;

    public CronField(final CronFieldName field, final FieldExpression expression, final FieldConstraints constraints) {
        this.field = field;
        this.expression = Preconditions.checkNotNull(expression, "FieldExpression must not be null");
        this.constraints = Preconditions.checkNotNull(constraints, "FieldConstraints must not be null");
    }

    public CronFieldName getField() {
        return field;
    }

    public FieldExpression getExpression() {
        return expression;
    }

    public FieldConstraints getConstraints() {
        return constraints;
    }

    public static Comparator<CronField> createFieldComparator() {
        return Comparator.comparingInt(o -> o.getField().getOrder());
    }

    @Override
    public String toString() {
        return "CronField{" + "field=" + field + '}';
    }
}

