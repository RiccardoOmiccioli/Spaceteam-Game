package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.Control;
import it.unibo.spaceteam.model.ControlType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ControlDeserializer implements JsonDeserializer<Control> {

    @Override
    public Control deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ControlType controlType = ControlType.valueOf(jsonObject.get("controlType").getAsString().toUpperCase());
        String controlLabel = jsonObject.get("controlLabel").getAsString();
        List<String> controlValues = new ArrayList<>();
        for (JsonElement value : jsonObject.getAsJsonArray("controlValues")) {
            controlValues.add(value.getAsString());
        }
        int controlWidth = jsonObject.get("controlWidth").getAsInt();
        int controlHeight = jsonObject.get("controlHeight").getAsInt();

        return new Control(controlType, controlLabel, controlValues, controlWidth, controlHeight);
    }

}
