package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.controller.GameController;
import it.unibo.spaceteam.game.GameEngine;

public class OnGameStart implements Event {

    public OnGameStart() {}

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.setController(new GameController(gameEngine));
        gameEngine.getController().update(gameEngine.getLobby());

        gameEngine.getMultiplayerService().subscribeControlInteraction();
        gameEngine.getMultiplayerService().subscribeOrderCompletion();
        gameEngine.getMultiplayerService().subscribeGameEnd();

        gameEngine.getMultiplayerService().publishLevelStart(1);
    }

}
