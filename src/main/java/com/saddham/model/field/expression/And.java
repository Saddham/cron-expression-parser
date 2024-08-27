package com.saddham.model.field.expression;

import com.saddham.model.field.expression.visitor.FieldExpressionVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class And extends FieldExpression {

    private static final long serialVersionUID = -3406340596495131941L;
    private final List<FieldExpression> expressions;

    public And() {
        expressions = new ArrayList<>();
    }

    @Override
    public And and(final FieldExpression exp) {
        expressions.add(exp);
        return this;
    }

    @Override
    public FieldExpression accept(FieldExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String asString() {
        final StringBuilder builder = new StringBuilder();
        for (int j = 0; j < expressions.size() - 1; j++) {
            builder.append(expressions.get(j).asString());
            builder.append(",");
        }
        if (expressions.size() > 0) {
            builder.append(expressions.get(expressions.size() - 1).asString());
        }
        return builder.toString();
    }

    public List<FieldExpression> getExpressions() {
        return Collections.unmodifiableList(expressions);
    }
}
