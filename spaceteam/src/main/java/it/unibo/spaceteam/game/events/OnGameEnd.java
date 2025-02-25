package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.controller.MainController;
import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.utils.PeriodicTimer;

public class OnGameEnd implements Event {

    public OnGameEnd() {}

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.getLobby().getGame().endGame();

        gameEngine.getController().update(gameEngine.getLobby());

        gameEngine.getMultiplayerService().leaveLobby();
        new PeriodicTimer(2000, 0, 0, () -> {
            gameEngine.setController(new MainController(gameEngine));
        }, (elapsedTime) -> {});
    }

}
