package com.saddham.model;

import com.saddham.model.definition.CronDefinition;
import com.saddham.model.field.CronField;
import com.saddham.model.field.CronFieldName;

import java.io.Serializable;
import java.util.Map;

public interface ICron extends Serializable {

    Map<CronFieldName, CronField> retrieveFieldsAsMap();

    String asString();

    CronDefinition getCronDefinition();

    ICron validate();
}

