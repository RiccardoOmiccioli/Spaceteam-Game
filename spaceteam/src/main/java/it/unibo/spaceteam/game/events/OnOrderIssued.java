package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Order;
import it.unibo.spaceteam.model.PlayerRole;
import it.unibo.spaceteam.utils.PeriodicTimer;
import it.unibo.spaceteam.view.Screen;

public class OnOrderIssued implements Event {

    public OnOrderIssued() {}

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();

        if (lobby.getCurrentPlayer().getPlayerRole() == PlayerRole.CAPTAIN) {
            Order order = lobby.getGame().issueNewOrder(lobby);

            order.setTimer(new PeriodicTimer(order.getMaxTime(), 1000 / Screen.getCurrentFrameRate(), 0, () -> {
                gameEngine.getLobby().getGame().getCurrentOrder().complete();
                gameEngine.getMultiplayerService().publishOrderCompletion(false);
            }, (timeElapsed) -> {
                order.setCurrentTime(timeElapsed);
                gameEngine.getController().update(gameEngine.getLobby());
            }));

            gameEngine.getController().update(gameEngine.getLobby());
        }
    }

}
