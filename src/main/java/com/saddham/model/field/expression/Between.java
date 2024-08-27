package com.saddham.model.field.expression;

import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;
import com.saddham.model.field.value.FieldValue;

public class Between extends FieldExpression {

    private static final long serialVersionUID = 549075258664100474L;
    private final FieldValue<?> from;
    private final FieldValue<?> to;

    public Between(final Between between) {
        this(between.getFrom(), between.getTo());
    }

    public Between(final FieldValue<?> from, final FieldValue<?> to) {
        this.from = from;
        this.to = to;
    }

    public FieldValue<?> getFrom() {
        return from;
    }

    public FieldValue<?> getTo() {
        return to;
    }

    @Override
    public FieldExpression accept(FieldExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String asString() {
        return String.format("%s-%s", from, to);
    }
}
