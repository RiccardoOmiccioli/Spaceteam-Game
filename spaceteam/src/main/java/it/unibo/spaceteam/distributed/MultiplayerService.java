package it.unibo.spaceteam.distributed;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.game.events.*;
import it.unibo.spaceteam.model.ControlInteraction;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerService {

    private final GameEngine gameEngine;
    private final MqttService mqttService;
    private final Map<Integer, VoteService> levelStartVotes = new HashMap<>();
    private VoteService gameEndVote;

    public MultiplayerService(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();
        this.gameEngine = gameEngine;
        this.mqttService = new MqttService(System.getProperty("mqttBrokerAddress"), lobby);
    }

    public void leaveLobby() {
        Player currentPlayer = gameEngine.getLobby().getCurrentPlayer();
        mqttService.publish(mqttService.getTopic().getPlayerTopic(currentPlayer.getId()), new byte[0], 1, true);
        mqttService.disconnect();
    }

    public void subscribePlayerUpdate() {
        mqttService.subscribe(mqttService.getTopic().getPlayerTopic() + "/#", (topic, message) -> {
            if (message.getPayload().length == 0) {
                String[] topicPath = topic.split("/");
                String playerId = topicPath[topicPath.length - 1];
                gameEngine.handleEvent(new OnPlayerDisconnect(gameEngine.getLobby().findPlayer(playerId)));
            } else {
                Player player = mqttService.retrievePayload(message, Player.class);
                gameEngine.handleEvent(new OnPlayerUpdate(player));
            }
        });
    }

    public void publishPlayerUpdate() {
        Player currentPlayer = gameEngine.getLobby().getCurrentPlayer();
        mqttService.publish(mqttService.getTopic().getPlayerTopic(currentPlayer.getId()), mqttService.createPayload(currentPlayer), 1, true);
    }

    public void subscribeLevelStart() {
        Lobby lobby = gameEngine.getLobby();
        mqttService.subscribe(mqttService.getTopic().getVoteLevelTopic() + "/#", (topic, message) -> {
            String[] topicPath = topic.split("/");
            int level = Integer.parseInt(topicPath[topicPath.length - 1]);
            Vote vote = mqttService.retrievePayload(message, Vote.class);
            if (!levelStartVotes.containsKey(level)) {
                if (level == 1) {
                    levelStartVotes.put(level, new VoteService(VotePolicy.ALL, lobby));
                } else {
                    levelStartVotes.put(level, new VoteService(VotePolicy.MAJORITY, lobby));
                }
            }
            if (level > lobby.getGame().getCurrentLevel().getLevel() + 1) {
                publishLevelStart(level);
                gameEngine.handleEvent(new OnLevelStart(level));
            } else if (!levelStartVotes.get(level).isVoteComplete() && level >= lobby.getGame().getCurrentLevel().getLevel()) {
                levelStartVotes.get(level).castVote(vote);
                if (levelStartVotes.get(level).isVoteComplete()) {
                    gameEngine.handleEvent(new OnLevelStart(level));
                }
            }
        });
    }

    public void publishLevelStart(int level) {
        Lobby lobby = gameEngine.getLobby();
        if (VoteService.canVote(lobby.getCurrentPlayer(), lobby, level)) {
            Vote vote = new Vote(lobby.getCurrentPlayer().getId(), true);
            mqttService.publish(mqttService.getTopic().getVoteLevelTopic() + "/" + level, mqttService.createPayload(vote), 1, false);
        }
    }

    public void subscribeGameEnd() {
        mqttService.subscribe(mqttService.getTopic().getVoteEndTopic(), (topic, message) -> {
            if (gameEndVote == null) {
                gameEndVote = new VoteService(VotePolicy.MAJORITY, gameEngine.getLobby());
            }
            Vote vote = mqttService.retrievePayload(message, Vote.class);
            if (!gameEndVote.isVoteComplete()) {
                gameEndVote.castVote(vote);
                if (gameEndVote.isVoteComplete()) {
                    gameEngine.handleEvent(new OnGameEnd());
                }
            }
        });
    }

    public void publishGameEnd() {
        Lobby lobby = gameEngine.getLobby();
        if (VoteService.canVote(lobby.getCurrentPlayer(), lobby, lobby.getGame().getCurrentLevel().getLevel())) {
            Vote vote = new Vote(lobby.getCurrentPlayer().getId(), true);
            mqttService.publish(mqttService.getTopic().getVoteEndTopic(), mqttService.createPayload(vote), 1, false);
        }
    }

    public void subscribeControlInteraction() {
        mqttService.subscribe(mqttService.getTopic().getControlInteractionTopic() + "/#", (topic, message) -> {
            String[] topicPath = topic.split("/");
            int level = Integer.parseInt(topicPath[topicPath.length - 1]);
            if (gameEngine.getLobby().getGame().getCurrentLevel().getLevel() == level) {
                ControlInteraction controlInteraction = mqttService.retrievePayload(message, ControlInteraction.class);
                gameEngine.handleEvent(new OnControlInteraction(controlInteraction));
            }
        });
    }

    public void publishControlInteraction(ControlInteraction controlInteraction) {
        Lobby lobby = gameEngine.getLobby();
        int level = lobby.getGame().getCurrentLevel().getLevel();
        mqttService.publish(mqttService.getTopic().getControlInteractionTopic() + "/" + level, mqttService.createPayload(controlInteraction), 0, false);
    }

    public void subscribeOrderCompletion() {
        mqttService.subscribe(mqttService.getTopic().getOrderCompletionTopic() + "/#", (topic, message) -> {
            String[] topicPath = topic.split("/");
            int level = Integer.parseInt(topicPath[topicPath.length - 1]);
            if (gameEngine.getLobby().getGame().getCurrentLevel().getLevel() == level) {
                boolean success = mqttService.retrievePayload(message, Boolean.class);
                gameEngine.handleEvent(new OnOrderCompletion(success));
            }
        });
    }

    public void publishOrderCompletion(boolean success) {
        Lobby lobby = gameEngine.getLobby();
        int level = lobby.getGame().getCurrentLevel().getLevel();
        mqttService.publish(mqttService.getTopic().getOrderCompletionTopic() + "/" + level, mqttService.createPayload(success), 2, false);
    }

}
