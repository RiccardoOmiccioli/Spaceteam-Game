package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.ControlInteraction;

import java.lang.reflect.Type;

public class ControlInteractionSerializer implements JsonSerializer<ControlInteraction> {

    @Override
    public JsonElement serialize(ControlInteraction controlInteraction, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("controlLabel", controlInteraction.getControlLabel());
        jsonObject.addProperty("controlValue", controlInteraction.getControlValue());
        jsonObject.addProperty("playerId", controlInteraction.getPlayerId());

        return jsonObject;
    }

}
