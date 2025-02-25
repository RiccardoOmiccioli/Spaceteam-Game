package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;
import it.unibo.spaceteam.model.PlayerRole;
import it.unibo.spaceteam.model.PlayerStatus;

public class OnPlayerUpdate implements Event {

    private Player player;

    public OnPlayerUpdate(Player player) {
        this.player = player;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        lobby.updatePlayer(player);

        gameEngine.getController().update(lobby);

        if (lobby.allMatch(player -> player.getPlayerStatus() == PlayerStatus.READY || player.getPlayerStatus() == PlayerStatus.PLAYING) &&
                lobby.anyMatch(player -> player.getPlayerRole() == PlayerRole.CAPTAIN) &&
                lobby.getCurrentPlayer().getPlayerStatus() == PlayerStatus.READY) {
                    gameEngine.handleEvent(new OnGameStart());
        }
    }

}
