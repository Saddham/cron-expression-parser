package com.saddham.model.field.constraint;

import java.io.Serializable;
import java.util.Objects;

public class FieldConstraints implements Serializable {

    private static final long serialVersionUID = -9112124669329704710L;
    private final Integer startRange;
    private final Integer endRange;

    public FieldConstraints(final int startRange, final int endRange) {
        this.startRange = startRange;
        this.endRange = endRange;
    }

    public int getStartRange() {
        return startRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public boolean isInRange(final int value) {
        return value >= getStartRange() && value <= getEndRange();
    }

    public boolean isPeriodInRange(final int period) {
        return period > 0 && period <= (getEndRange() - getStartRange() + 1) && period <= getEndRange();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldConstraints that = (FieldConstraints) o;
        return startRange.equals(that.startRange) && endRange.equals(that.endRange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startRange, endRange);
    }
}
