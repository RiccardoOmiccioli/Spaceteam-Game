package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.PlayerStatus;

public class OnLevelStart implements Event {

    private final int level;

    public OnLevelStart(int level) {
        this.level = level;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        if (lobby.getCurrentPlayer().getPlayerStatus() == PlayerStatus.READY) {
            gameEngine.getLobby().getCurrentPlayer().setPlayerStatus(PlayerStatus.PLAYING);
            gameEngine.getMultiplayerService().publishPlayerUpdate();
        }
        gameEngine.getLobby().getGame().startNewLevel(level);
        gameEngine.getController().update(gameEngine.getLobby());
        gameEngine.handleEvent(new OnOrderIssued());
    }

}
