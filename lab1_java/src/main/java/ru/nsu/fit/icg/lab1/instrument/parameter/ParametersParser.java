package ru.nsu.fit.icg.lab1.instrument.parameter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ParametersParser {

    private final JSONParser parser = new JSONParser();
    private final JSONObject json = (JSONObject) parser
            .parse(new String(getClass().getResourceAsStream("parameters.json")
                    .readAllBytes(), StandardCharsets.UTF_8));

    private final Map<String, Map<String, Parameter>> parameters = new HashMap<>();

    public ParametersParser() throws IOException, ParseException {
    }

    public Map<String, Parameter> getParametersMap(String instrumentName) {
        return parameters.get(instrumentName);
    }

    public Map<String, Value> getValues(String instrumentName, String[] parameterGroupNames) {
        Map<String, Value> instrumentValues = new HashMap<>();
        Map<String, Parameter> instrumentParameters = new HashMap<>();
        for (String parameterGroupName : parameterGroupNames) {
            JSONArray parametersArray = (JSONArray) json.get(parameterGroupName);
            for (Object pararameterObject : parametersArray) {
                JSONObject parameterJsonObject = (JSONObject) pararameterObject;
                Parameter parameter;
                String parameterName = (String) parameterJsonObject.get("name");
                boolean requiresValue = (boolean) parameterJsonObject.get("requiresValue");
                parameter = new Parameter(
                        parameterName,
                        (int) (long) parameterJsonObject.get("min"),
                        (int) (long) parameterJsonObject.get("max"),
                        (int) (long) parameterJsonObject.get("default"),
                        requiresValue,
                        requiresValue ? true : (boolean) parameterJsonObject.get("useValue"),
                        (int) (long) parameterJsonObject.get("minorTicks"),
                        (int) (long) parameterJsonObject.get("majorTicks"));
                instrumentValues.put(parameterName, parameter.getValue());
                instrumentParameters.put(parameterName, parameter);
            }
        }
        parameters.put(instrumentName, instrumentParameters);
        return instrumentValues;
    }
}
