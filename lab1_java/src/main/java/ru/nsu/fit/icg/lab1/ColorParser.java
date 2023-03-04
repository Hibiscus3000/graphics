package ru.nsu.fit.icg.lab1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.nsu.fit.icg.lab1.action.ColorAction;
import ru.nsu.fit.icg.lab1.panel.ColorListener;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ColorParser {

    public static List<ColorAction> parseColorActionsJson(ColorListener colorListener) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonColorArray = (JSONArray) ((JSONObject)parser.parse(
                new String(ColorParser.class.getResourceAsStream("colors.json")
                .readAllBytes(), StandardCharsets.UTF_8))).get("colors");
        List<ColorAction> colorActions = new ArrayList<>();
        for (Object colorObject : jsonColorArray) {
            JSONObject jsonColorObject = (JSONObject) colorObject;
            colorActions.add(
                    new ColorAction(
                            (String) jsonColorObject.get("name"),
                            (String) jsonColorObject.get("nameInstrumentalCase"),
                            new Color(
                                    (int) (long)jsonColorObject.get("red"),
                                    (int) (long) jsonColorObject.get("green"),
                                    (int) (long) jsonColorObject.get("blue")),
                            colorListener,
                            (String) jsonColorObject.get("iconFileName")
                    ));
        }

        return colorActions;
    }
}
