package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.Control;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.utils.GridPosition;

import java.lang.reflect.Type;
import java.util.Map;

public class ControlPanelSerializer implements JsonSerializer<ControlPanel> {

    @Override
    public JsonElement serialize(ControlPanel controlPanel, Type type, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();

        for (Map.Entry<Control, GridPosition> entry : controlPanel.getControls().entrySet()) {
            Control control = entry.getKey();
            JsonElement controlJson = context.serialize(control);
            jsonArray.add(controlJson);
        }

        return jsonArray;
    }

}
