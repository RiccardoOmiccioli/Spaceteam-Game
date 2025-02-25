package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.model.Player;

import java.lang.reflect.Type;

public class PlayerSerializer implements JsonSerializer<Player> {

    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", player.getId());
        jsonObject.addProperty("username", player.getUsername());
        jsonObject.addProperty("role", player.getPlayerRole().name());
        jsonObject.addProperty("status", player.getPlayerStatus().name());
        if (player.getControlPanel() != null) {
            JsonElement jsonElement = context.serialize(player.getControlPanel());
            jsonObject.add("controlPanel", jsonElement);
        }

        return jsonObject;
    }

}
