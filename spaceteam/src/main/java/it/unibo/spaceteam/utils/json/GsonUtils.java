package it.unibo.spaceteam.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unibo.spaceteam.distributed.Vote;
import it.unibo.spaceteam.model.*;

public class GsonUtils {

    public static Gson createGson() {
        return new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .serializeNulls()
            .registerTypeAdapter(Control.class, new ControlSerializer())
            .registerTypeAdapter(Control.class, new ControlDeserializer())
            .registerTypeAdapter(ControlInteraction.class, new ControlInteractionSerializer())
            .registerTypeAdapter(ControlInteraction.class, new ControlInteractionDeserializer())
            .registerTypeAdapter(ControlPanel.class, new ControlPanelSerializer())
            .registerTypeAdapter(ControlPanel.class, new ControlPanelDeserializer())
            .registerTypeAdapter(Player.class, new PlayerSerializer())
            .registerTypeAdapter(Player.class, new PlayerDeserializer())
            .registerTypeAdapter(Vote.class, new VoteSerializer())
            .registerTypeAdapter(Vote.class, new VoteDeserializer())
            .create();
    }

}
