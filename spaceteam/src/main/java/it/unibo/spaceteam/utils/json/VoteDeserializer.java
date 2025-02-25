package it.unibo.spaceteam.utils.json;

import com.google.gson.*;
import it.unibo.spaceteam.distributed.Vote;

import java.lang.reflect.Type;

public class VoteDeserializer implements JsonDeserializer<Vote> {

    @Override
    public Vote deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String voterId = jsonObject.get("voterId").getAsString();
        Boolean voteValue = jsonObject.get("voteValue").getAsBoolean();

        return new Vote(voterId, voteValue);
    }

}
