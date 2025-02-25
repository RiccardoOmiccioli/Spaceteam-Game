package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.Control;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.utils.GridPosition;

import java.lang.reflect.Type;
import java.util.*;

public class ControlPanelDeserializer implements JsonDeserializer<ControlPanel> {

    @Override
    public ControlPanel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<Control, GridPosition> controls = new HashMap<>();

        if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                Control control = context.deserialize(element, Control.class);
                controls.put(control, null);
            }
        }

        return new ControlPanel(controls);
    }

}
