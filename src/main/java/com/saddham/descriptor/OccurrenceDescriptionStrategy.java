package com.saddham.descriptor;

import com.google.common.base.Preconditions;
import com.saddham.model.field.definition.FieldDefinition;
import com.saddham.model.field.expression.Always;
import com.saddham.model.field.expression.And;
import com.saddham.model.field.expression.Between;
import com.saddham.model.field.expression.Every;
import com.saddham.model.field.expression.FieldExpression;
import com.saddham.model.field.expression.On;
import com.saddham.model.field.value.IntegerFieldValue;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class OccurrenceDescriptionStrategy {
    private final FieldDefinition fieldDefinition;

    public OccurrenceDescriptionStrategy(FieldDefinition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    protected Set<Integer> describe(final FieldExpression fieldExpression) {
        Preconditions.checkNotNull(fieldExpression, "CronFieldExpression should not be null!");

        Set<Integer> occurrences = new TreeSet<>();
        if (fieldExpression instanceof Always) {
            occurrences.addAll(describe((Always) fieldExpression));
        } else if (fieldExpression instanceof And) {
            occurrences.addAll(describe((And) fieldExpression));
        } else if (fieldExpression instanceof Between) {
            occurrences.addAll(describe((Between) fieldExpression));
        } else if (fieldExpression instanceof Every) {
            occurrences.addAll(describe((Every) fieldExpression));
        } else if (fieldExpression instanceof On) {
            occurrences.addAll(describe((On) fieldExpression));
        }

        return occurrences;
    }

    protected Set<Integer> describe(final Always always) {
        int start = fieldDefinition.getConstraints().getStartRange();
        int end = fieldDefinition.getConstraints().getEndRange();

        return IntStream.range(start, end + 1).boxed().collect(Collectors.toSet());
    }

    protected Set<Integer> describe(final And and) {
        Set<Integer> occurrences = new HashSet<>();
        for (final FieldExpression fieldExpression : and.getExpressions()) {
            occurrences.addAll(describe(fieldExpression));
        }

        return occurrences;
    }

    protected Set<Integer> describe(final Between between) {
        final int start = ((IntegerFieldValue) between.getFrom()).getValue();
        final int end = ((IntegerFieldValue) between.getTo()).getValue();

        return IntStream.range(start, end + 1).boxed().collect(Collectors.toSet());
    }

    protected Set<Integer> describe(final Every every) {
        int period = every.getPeriod().getValue();
        int start = fieldDefinition.getConstraints().getStartRange();
        int end = fieldDefinition.getConstraints().getEndRange();

        if (every.getExpression() instanceof Between) {
            final Between between = (Between) every.getExpression();
            start = ((IntegerFieldValue) between.getFrom()).getValue();
            end = ((IntegerFieldValue) between.getTo()).getValue();
        }

        final int startNum = start;
        final int endNum = end;

        Set<Integer> occurrences = IntStream.range(startNum, endNum + 1)
                .filter(n -> n % period == 0)
                .boxed() // Convert IntStream to Stream<Integer>
                .collect(Collectors.toSet());

        return occurrences;
    }

    protected Set<Integer> describe(final On on) {
        Integer occurrence = on.getTime().getValue();

        return Set.of(occurrence);
    }
}
