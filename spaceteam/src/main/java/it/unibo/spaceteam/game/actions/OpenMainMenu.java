package it.unibo.spaceteam.game.actions;

import it.unibo.spaceteam.controller.MainController;
import it.unibo.spaceteam.game.GameEngine;

public class OpenMainMenu implements Action {

    @Override
    public void execute(GameEngine gameEngine) {
        if (gameEngine.getMultiplayerService() != null) {
            gameEngine.getMultiplayerService().leaveLobby();
        }

        gameEngine.setController(new MainController(gameEngine));
    }
}
