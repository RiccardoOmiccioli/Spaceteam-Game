package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.Control;

import java.lang.reflect.Type;

public class ControlSerializer implements JsonSerializer<Control> {

    @Override
    public JsonElement serialize(Control control, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("controlType", control.getControlType().name());
        jsonObject.addProperty("controlLabel", control.getControlLabel());
        JsonArray controlValues = new JsonArray();
        for (String value: control.getControlValues()) {
            controlValues.add(value);
        }
        jsonObject.add("controlValues", controlValues);
        jsonObject.addProperty("controlWidth", control.getControlWidth());
        jsonObject.addProperty("controlHeight", control.getControlHeight());

        return jsonObject;
    }

}
