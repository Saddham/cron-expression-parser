package com.saddham.model.field.constraint;

import com.saddham.model.field.CronFieldName;

public class FieldConstraintsBuilder {
    private int startRange;
    private int endRange;

    private FieldConstraintsBuilder() {
        startRange = 0;//no negatives!
        endRange = Integer.MAX_VALUE;
    }

    public FieldConstraintsBuilder forField(final CronFieldName field) {
        switch (field) {
            case MINUTE:
                endRange = 59;
                return this;
            case HOUR:
                endRange = 23;
                return this;
            case DAY_OF_WEEK:
                endRange = 6;
                return this;
            case DAY_OF_MONTH:
                startRange = 1;
                endRange = 31;
                return this;
            case MONTH:
                startRange = 1;
                endRange = 12;
                return this;
            default:
                return this;
        }
    }

    public FieldConstraintsBuilder withValidRange(final int startRange, final int endRange) {
        this.startRange = startRange;
        this.endRange = endRange;
        return this;
    }

    public FieldConstraints createConstraintsInstance() {
        return new FieldConstraints(startRange, endRange);
    }

    public static FieldConstraintsBuilder instance() {
        return new FieldConstraintsBuilder();
    }
}

