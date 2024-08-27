package com.saddham.model.field.expression;

import com.google.common.base.Preconditions;
import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;
import com.saddham.model.field.value.IntegerFieldValue;

public class On extends FieldExpression {

    private static final long serialVersionUID = 8746471281123327324L;
    private final IntegerFieldValue time;

    public On(final IntegerFieldValue time) {
        Preconditions.checkNotNull(time, "time must not be null");

        this.time = time;
    }

    public IntegerFieldValue getTime() {
        return time;
    }

    @Override
    public FieldExpression accept(FieldExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String asString() {
        return getTime().toString();
    }
}
