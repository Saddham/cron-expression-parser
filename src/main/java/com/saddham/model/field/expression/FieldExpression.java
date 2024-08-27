package com.saddham.model.field.expression;

import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;

import java.io.Serializable;

public abstract class FieldExpression implements Serializable {

    private static final long serialVersionUID = 5138279438874391617L;

    public And and(final FieldExpression exp) {
        return new And().and(this).and(exp);
    }

    public abstract String asString();

    public abstract FieldExpression accept(final FieldExpressionVisitor visitor);

    public static FieldExpression always() {
        return Always.INSTANCE;
    }
}
