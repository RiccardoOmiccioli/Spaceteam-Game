package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.ControlInteraction;

import java.lang.reflect.Type;

public class ControlInteractionDeserializer implements JsonDeserializer<ControlInteraction> {

    @Override
    public ControlInteraction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String controlLabel = jsonObject.get("controlLabel").getAsString();
        String controlValue = jsonObject.get("controlValue").getAsString();
        String playerId = jsonObject.get("playerId").getAsString();

        return new ControlInteraction(controlLabel, controlValue, playerId);
    }

}
