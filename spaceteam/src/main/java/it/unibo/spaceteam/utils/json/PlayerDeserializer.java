package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.model.Player;
import it.unibo.spaceteam.model.PlayerRole;
import it.unibo.spaceteam.model.PlayerStatus;

import java.lang.reflect.Type;

public class PlayerDeserializer implements JsonDeserializer<Player> {

    @Override
    public Player deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String id = jsonObject.get("id").getAsString();
        String username = jsonObject.get("username").getAsString();
        PlayerRole playerRole = PlayerRole.valueOf(jsonObject.get("role").getAsString());
        PlayerStatus playerStatus = PlayerStatus.valueOf(jsonObject.get("status").getAsString());

        ControlPanel controlPanel = null;
        if (jsonObject.has("controlPanel")) {
            JsonElement controlPanelElement = jsonObject.get("controlPanel");
            controlPanel = context.deserialize(controlPanelElement, ControlPanel.class);
        }

        return new Player(id, username, playerRole, playerStatus, controlPanel);
    }

}
