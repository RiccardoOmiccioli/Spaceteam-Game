package it.unibo.spaceteam.game.actions;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.PlayerStatus;

public class ChangeStatus implements Action {

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        switch (lobby.getCurrentPlayer().getPlayerStatus()) {
            case ONLINE -> lobby.getCurrentPlayer().setPlayerStatus(PlayerStatus.READY);
            case READY -> lobby.getCurrentPlayer().setPlayerStatus(PlayerStatus.ONLINE);
            case OFFLINE -> {}
            case PLAYING -> {}
        }

        gameEngine.getController().update(lobby);
        gameEngine.getMultiplayerService().publishPlayerUpdate();
    }

}
