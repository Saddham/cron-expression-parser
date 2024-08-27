package com.saddham.model.definition;

import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.definition.FieldDefinition;
import com.saddham.model.field.definition.FieldDefinitionBuilder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CronDefinitionBuilder {
    private final Map<CronFieldName, FieldDefinition> fields = new EnumMap<>(CronFieldName.class);

    private CronDefinitionBuilder() {/*NOP*/}

    public static CronDefinitionBuilder defineCron() {
        return new CronDefinitionBuilder();
    }

    public FieldDefinitionBuilder withMinutes() {
        return new FieldDefinitionBuilder(this, CronFieldName.MINUTE);
    }

    public FieldDefinitionBuilder withHours() {
        return new FieldDefinitionBuilder(this, CronFieldName.HOUR);
    }

    public FieldDefinitionBuilder withDayOfMonth() {
        return new FieldDefinitionBuilder(this, CronFieldName.DAY_OF_MONTH);
    }

    public FieldDefinitionBuilder withMonth() {
        return new FieldDefinitionBuilder(this, CronFieldName.MONTH);
    }

    public FieldDefinitionBuilder withDayOfWeek() {
        return new FieldDefinitionBuilder(this, CronFieldName.DAY_OF_WEEK);
    }

    public void register(final FieldDefinition definition) {
        fields.put(definition.getFieldName(), definition);
    }

    public CronDefinition instance() {
        final List<FieldDefinition> values = new ArrayList<>(fields.values());
        values.sort(FieldDefinition.createFieldDefinitionComparator());
        return new CronDefinition(values);
    }

    public static CronDefinition unixCron() {
        return CronDefinitionBuilder.defineCron()
                .withMinutes().withValidRange(0, 59).and()
                .withHours().withValidRange(0, 23).and()
                .withDayOfMonth().withValidRange(1, 31).and()
                .withMonth().withValidRange(1, 12).and()
                .withDayOfWeek().withValidRange(0, 7).and()
                .instance();
    }
}