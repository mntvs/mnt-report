package com.mntviews.jreport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mntviews.jreport.exception.JReportSchemaValidatorException;
import com.networknt.schema.*;

import java.util.Set;
import java.util.stream.Collectors;

public class JRSchemaValidator {
    private static final String SCHEMA_FILE_NAME = "jreport-package.schema.json";
    private static final JsonSchema schema;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JRSchemaValidator() {
    }

    static void validate(JsonNode data) {
        Set<ValidationMessage> errors = null;
        errors = schema.validate(data);
        if (errors != null && !errors.isEmpty()) {
            throw new JReportSchemaValidatorException(errors.stream().map(ValidationMessage::getMessage).collect(Collectors.joining(System.lineSeparator())));
        }
    }

    static void validate(String data) {
        try {
            JRSchemaValidator.validate(objectMapper.readTree(data));
        } catch (JsonProcessingException e) {
            throw new JReportSchemaValidatorException(e);
        }
    }

    static {
        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
        config.setTypeLoose(false);
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        schema = factory.getSchema(JRSchemaValidator.class.getClassLoader().getResourceAsStream(SCHEMA_FILE_NAME), config);
    }


}
