package ru.nsu.fit.icg.lab2.filter.dialog;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FilterDialogFactory {

    private final Map<String, Constructor> dialogConstructorsMap = new HashMap<>();

    private static volatile FilterDialogFactory instance;

    private FilterDialogFactory() {
        final JSONParser jsonParser = new JSONParser();
        try {
            final byte[] jsonBytes = getClass().getResourceAsStream("dialogs.json").readAllBytes();
            final JSONObject dialogsJsonObject = (JSONObject)
                    jsonParser.parse(new String(jsonBytes, StandardCharsets.UTF_8));
            final JSONArray dialogJsonArray = (JSONArray) dialogsJsonObject.get("filters");
            for (Object dialogObject : dialogJsonArray) {
                JSONObject dialogJsonObject = (JSONObject) dialogObject;
                Class dialogClass = Class.forName((String) dialogJsonObject.get("dialogClass"));
                dialogConstructorsMap.put((String) dialogJsonObject.get("name"),
                        dialogClass.getDeclaredConstructor());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FilterDialogFactory getInstance() {
        if (null == instance) {
            synchronized (FilterDialogFactory.class) {
                if (null == instance) {
                    instance = new FilterDialogFactory();
                }
            }
        }
        return instance;
    }

    public Constructor getConstructor(String filterName) {
        return dialogConstructorsMap.get(filterName);
    }
}
