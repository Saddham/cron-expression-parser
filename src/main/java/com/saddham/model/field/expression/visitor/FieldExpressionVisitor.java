package com.saddham.model.field.expression.visitor;

import com.saddham.model.field.expression.Always;
import com.saddham.model.field.expression.And;
import com.saddham.model.field.expression.Between;
import com.saddham.model.field.expression.Every;
import com.saddham.model.field.expression.FieldExpression;
import com.saddham.model.field.expression.On;

public interface FieldExpressionVisitor {

    FieldExpression visit(Always always);

    FieldExpression visit(And and);

    FieldExpression visit(Between between);

    FieldExpression visit(Every every);

    FieldExpression visit(On on);
}

