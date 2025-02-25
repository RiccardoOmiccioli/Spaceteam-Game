package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.controller.GameController;
import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;
import it.unibo.spaceteam.model.PlayerRole;

public class OnPlayerDisconnect implements Event {

    private Player player;

    public OnPlayerDisconnect(Player player) {
        this.player = player;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        lobby.removePlayer(player);
        if (gameEngine.getController().getClass() == GameController.class
                && !lobby.anyMatch(player -> player.getPlayerRole() == PlayerRole.CAPTAIN)) {
                    gameEngine.getMultiplayerService().publishGameEnd();
        }

        gameEngine.getController().update(lobby);
    }

}
