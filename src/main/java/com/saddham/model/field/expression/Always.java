package com.saddham.model.field.expression;

import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;

public class Always extends FieldExpression {

    private static final long serialVersionUID = -1221923855248365713L;
    static final Always INSTANCE = new Always();

    private Always() {
    }

    @Override
    public FieldExpression accept(FieldExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String asString() {
        return "*";
    }

    @Override
    public String toString() {
        return "Always{}";
    }
}
