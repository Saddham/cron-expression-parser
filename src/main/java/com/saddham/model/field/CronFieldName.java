package com.saddham.model.field;

public enum CronFieldName {
    MINUTE("minute", 1), HOUR("hour", 2), DAY_OF_MONTH("day of month", 3), MONTH("month", 4), DAY_OF_WEEK("day of week", 5);

    private String name;
    private int order;

    CronFieldName(final String name, final int order) {
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }
}
