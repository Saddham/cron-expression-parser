package com.saddham.model.definition;

import com.saddham.model.field.CronFieldName;
import com.saddham.model.field.definition.FieldDefinition;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CronDefinition implements Serializable {

    private static final long serialVersionUID = 7067112327461432170L;

    private final Map<CronFieldName, FieldDefinition> fieldDefinitions;

    public CronDefinition(final List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = new EnumMap<>(CronFieldName.class);
        for (final FieldDefinition field : fieldDefinitions) {
            this.fieldDefinitions.put(field.getFieldName(), field);
        }
    }

    public Set<FieldDefinition> getFieldDefinitions() {
        return new HashSet<>(fieldDefinitions.values());
    }

    public Map<CronFieldName, FieldDefinition> retrieveFieldDefinitionsAsMap() {
        return Collections.unmodifiableMap(fieldDefinitions);
    }

    public FieldDefinition getFieldDefinition(final CronFieldName cronFieldName) {
        return fieldDefinitions.get(cronFieldName);
    }

    public boolean containsFieldDefinition(final CronFieldName cronFieldName) {
        return fieldDefinitions.containsKey(cronFieldName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CronDefinition that = (CronDefinition) o;
        return fieldDefinitions.equals(that.fieldDefinitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldDefinitions);
    }
}

