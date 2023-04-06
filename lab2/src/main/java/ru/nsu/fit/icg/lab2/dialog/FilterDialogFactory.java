package ru.nsu.fit.icg.lab2.dialog;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.nsu.fit.icg.lab2.filter.Filter;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FilterDialogFactory {

    private final Map<String, Constructor<FilterDialog>> dialogConstructorsMap = new HashMap<>();

    private static volatile FilterDialogFactory instance;

    private FilterDialogFactory() {
        final JSONParser jsonParser = new JSONParser();
        try {
            final byte[] jsonBytes = getClass().getResourceAsStream("filterDialogs.json").readAllBytes();
            final JSONObject dialogsJsonObject = (JSONObject)
                    jsonParser.parse(new String(jsonBytes, StandardCharsets.UTF_8));
            final JSONArray dialogJsonArray = (JSONArray) dialogsJsonObject.get("filters");
            for (Object dialogObject : dialogJsonArray) {
                JSONObject dialogJsonObject = (JSONObject) dialogObject;
                Class dialogClass = Class.forName((String) dialogJsonObject.get("dialogClass"));
                dialogConstructorsMap.put((String) dialogJsonObject.get("name"),
                        dialogClass.getDeclaredConstructors()[0]);
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

    private final Map<String, FilterDialog> filterDialogs = new HashMap<>();

    public FilterDialog getFilterDialog(Filter filter) {
        String filterJsonName = filter.getJsonName();
        FilterDialog filterDialog = filterDialogs.get(filterJsonName);
        if (null == filterDialog) {
            try {
                Constructor<FilterDialog> filterDialogConstructor = dialogConstructorsMap.get(filterJsonName);
                if (null == filterDialogConstructor) {
                    return null;
                }
                filterDialog = filterDialogConstructor.newInstance(filter);
                filterDialogs.put(filterJsonName, filterDialog);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return filterDialog;
    }

    public boolean hasParameters(String filterName) {
        return null != dialogConstructorsMap.get(filterName);
    }
}
