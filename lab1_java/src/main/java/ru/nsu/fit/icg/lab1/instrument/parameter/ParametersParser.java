package ru.nsu.fit.icg.lab1.instrument.parameter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParametersParser {

    private final JSONParser parser = new JSONParser();
    private final JSONObject json = (JSONObject) parser
            .parse(new String(getClass().getResourceAsStream("parameters.json")
                    .readAllBytes(), StandardCharsets.UTF_8));

    public ParametersParser() throws IOException, ParseException {
    }

    private final Map<String, List<String>> parameterNameMap = new HashMap<>();
    private final Map<String, Parameter> parametersMap = new HashMap<>();

    public Map<String, Parameter> getParametersMap(String[] parameterGroupNames) {
        Map<String, Parameter> targetGroupsParameters = new HashMap<>();
        for (String parameterGroupName : parameterGroupNames) {
            for (String parameterName :
                    parameterNameMap.get(parameterGroupName)) {
                targetGroupsParameters.put(parameterName, parametersMap.get(parameterName));
            }
        }
        return targetGroupsParameters;
    }

    public Map<String, Integer> getDefaults(String[] parameterGroupNames) {
        Map<String, Integer> targetGroupDefaults = new HashMap<>();

        for (String parameterGroupName : parameterGroupNames) {
            List<String> parameterNames;
            if (null == (parameterNames = parameterNameMap.get(parameterGroupName))) {
                parameterNames = new ArrayList<>();
                parameterNameMap.put(parameterGroupName, parameterNames);
                JSONArray parametersArray = (JSONArray) json.get(parameterGroupName);
                for (Object pararameterObject : parametersArray) {
                    JSONObject parameterJsonObject = (JSONObject) pararameterObject;
                    String parameterName = (String) parameterJsonObject.get("name");
                    parametersMap.put(parameterName, new Parameter(
                            (String) parameterJsonObject.get("guiName"),
                            (int) (long) parameterJsonObject.get("lowerBorder"),
                            (int) (long) parameterJsonObject.get("upperBorder"),
                            (int) (long) parameterJsonObject.get("default"),
                            (int) (long) parameterJsonObject.get("minorTicks"),
                            (int) (long) parameterJsonObject.get("majorTicks"),
                            (boolean) parameterJsonObject.get("requiresValue")));
                    parameterNames.add(parameterName);
                }
            }
            for (String parameterName : parameterNames) {
                targetGroupDefaults.put(parameterName,
                        parametersMap.get(parameterName).getValue());
            }

        }
        return targetGroupDefaults;
    }
}
