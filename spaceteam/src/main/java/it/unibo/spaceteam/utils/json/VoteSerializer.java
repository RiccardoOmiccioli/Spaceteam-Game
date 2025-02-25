package it.unibo.spaceteam.utils.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unibo.spaceteam.distributed.Vote;

import java.lang.reflect.Type;

public class VoteSerializer implements JsonSerializer<Vote> {

    @Override
    public JsonElement serialize(Vote vote, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("voterId", vote.getVoterId());
        jsonObject.addProperty("voteValue", vote.getVoteValue());
        return jsonObject;
    }

}
