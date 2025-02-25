package it.unibo.spaceteam.game.actions;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.PlayerRole;

public class ChangeRole implements Action {

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        switch (lobby.getCurrentPlayer().getPlayerRole()) {
            case CAPTAIN -> lobby.getCurrentPlayer().setPlayerRole(PlayerRole.CREW_MEMBER);
            case CREW_MEMBER -> lobby.getCurrentPlayer().setPlayerRole(PlayerRole.CAPTAIN);
        }

        gameEngine.getController().update(lobby);
        gameEngine.getMultiplayerService().publishPlayerUpdate();

    }

}
