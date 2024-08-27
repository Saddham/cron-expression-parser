package com.saddham.model.field.expression.visitor;

import com.saddham.model.field.constraint.FieldConstraints;
import com.saddham.model.field.expression.Always;
import com.saddham.model.field.expression.And;
import com.saddham.model.field.expression.Between;
import com.saddham.model.field.expression.Every;
import com.saddham.model.field.expression.FieldExpression;
import com.saddham.model.field.expression.On;
import com.saddham.model.field.value.FieldValue;
import com.saddham.model.field.value.IntegerFieldValue;

public class ValidationFieldExpressionVisitor implements FieldExpressionVisitor {
    private static final String OORANGE = "Value %s not in range [%s, %s]";

    private final FieldConstraints constraints;

    public ValidationFieldExpressionVisitor(final FieldConstraints constraints) {
        this.constraints = constraints;
    }

    @Override
    public Always visit(final Always always) {
        return always;
    }

    @Override
    public And visit(final And and) {
        for (final FieldExpression expression : and.getExpressions()) {
            expression.accept(this);
        }
        return and;
    }

    @Override
    public Between visit(final Between between) {
        preConditions(between);

        if (between.getFrom() instanceof IntegerFieldValue && between.getTo() instanceof IntegerFieldValue) {
            final int from = ((IntegerFieldValue) between.getFrom()).getValue();
            final int to = ((IntegerFieldValue) between.getTo()).getValue();
            if (from > to) {
                throw new IllegalArgumentException(String.format("Invalid range! [%s,%s]", from, to));
            }
        }

        return between;
    }

    @Override
    public Every visit(final Every every) {
        if (every.getExpression() != null)
            every.getExpression().accept(this);
        isPeriodInRange(every.getPeriod());
        return every;
    }

    @Override
    public On visit(final On on) {
        isInRange(on.getTime());

        return on;
    }

    private void preConditions(final Between between) {
        isInRange(between.getFrom());
        isInRange(between.getTo());
    }

    protected void isInRange(final FieldValue<?> fieldValue) {
        if (fieldValue instanceof IntegerFieldValue) {
            final int value = ((IntegerFieldValue) fieldValue).getValue();
            if (!constraints.isInRange(value)) {
                throw new IllegalArgumentException(String.format(OORANGE, value, constraints.getStartRange(), constraints.getEndRange()));
            }
        }
    }

    protected void isPeriodInRange(final FieldValue<?> fieldValue) {
        if (fieldValue instanceof IntegerFieldValue) {
            final int value = ((IntegerFieldValue) fieldValue).getValue();
            if (!constraints.isPeriodInRange(value)) {
                throw new IllegalArgumentException(
                        String.format("Period %s not in range [%s, %s]", value, constraints.getStartRange(), constraints.getEndRange()));
            }
        }
    }
}
