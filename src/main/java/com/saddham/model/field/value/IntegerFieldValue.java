package com.saddham.model.field.value;

public class IntegerFieldValue extends FieldValue<Integer> {

    private static final long serialVersionUID = -1305795676868267699L;
    private final int value;

    public IntegerFieldValue(final int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}

