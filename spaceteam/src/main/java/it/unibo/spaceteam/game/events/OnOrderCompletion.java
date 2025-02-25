package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Level;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.PlayerRole;

public class OnOrderCompletion implements Event {

    private boolean success;

    public OnOrderCompletion(boolean success) {
        this.success = success;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();
        Level currentLevel = lobby.getGame().getCurrentLevel();

        if (success) {
            currentLevel.completeOrder();
            if (currentLevel.isLevelCompleted()) {
                gameEngine.getMultiplayerService().publishLevelStart(1); // Unlock waiting players
                gameEngine.getMultiplayerService().publishLevelStart(currentLevel.getLevel() + 1);
            }
        } else {
            currentLevel.takeDamage();
            if (currentLevel.isIntegrityCompromised()) {
                gameEngine.getMultiplayerService().publishGameEnd();
            }
        }
        if (lobby.getCurrentPlayer().getPlayerRole() == PlayerRole.CAPTAIN
                &&lobby.getGame().getCurrentOrder().isComplete()
                && !currentLevel.isLevelCompleted()
                && !currentLevel.isIntegrityCompromised()) {
                    gameEngine.handleEvent(new OnOrderIssued());
        }
        gameEngine.getController().update(gameEngine.getLobby());
    }

}
