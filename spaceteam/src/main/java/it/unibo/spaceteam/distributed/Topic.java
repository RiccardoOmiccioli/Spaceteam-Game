package it.unibo.spaceteam.distributed;

public class Topic {

    private final String ROOT_TOPIC = "spaceteam/lobby";
    private final String PLAYER_TOPIC = "player";
    private final String VOTE_TOPIC = "vote";

    private final String lobbyId;

    public Topic(String lobbyId) {
        if (lobbyId == null || lobbyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Null or Empty lobby identifier");
        }
        this.lobbyId = lobbyId;
    }

    public String getPlayerTopic() {
        return constructTopic(ROOT_TOPIC, lobbyId, PLAYER_TOPIC);
    }

    public String getPlayerTopic(String playerId) {
        return constructTopic(ROOT_TOPIC, lobbyId, PLAYER_TOPIC, playerId);
    }

    public String getVoteLevelTopic() {
        return constructTopic(ROOT_TOPIC, lobbyId, VOTE_TOPIC, "level");
    }

    public String getVoteEndTopic() {
        return constructTopic(ROOT_TOPIC, lobbyId, VOTE_TOPIC, "end");
    }

    public String getControlInteractionTopic() {
        return constructTopic(ROOT_TOPIC, lobbyId, "control");
    }

    public String getOrderCompletionTopic() {
        return constructTopic(ROOT_TOPIC, lobbyId, "order");
    }

    private String constructTopic(String... segments) {
        return String.join("/", segments);
    }

}
