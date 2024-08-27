package com.saddham.model.field.expression;

import com.google.common.base.Preconditions;
import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;
import com.saddham.model.field.value.IntegerFieldValue;

public class Every extends FieldExpression {

    private static final long serialVersionUID = -1103196842332906994L;
    private final FieldExpression expression;
    private final IntegerFieldValue period;

    public Every(final IntegerFieldValue time) {
        this(always(), time);
    }

    public Every(final FieldExpression expression, final IntegerFieldValue period) {
        this.expression = Preconditions.checkNotNull(expression, "Expression must not be null");
        this.period = period == null ? new IntegerFieldValue(1) : period;
    }

    public IntegerFieldValue getPeriod() {
        return period;
    }

    public FieldExpression getExpression() {
        return expression;
    }

    @Override
    public FieldExpression accept(FieldExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String asString() {
        final String expressionAsString = expression.asString();
        if ("*".equals(expressionAsString) && period.getValue() == 1) {
            return expressionAsString;
        }
        return String.format("%s/%s", expressionAsString, period);
    }
}
