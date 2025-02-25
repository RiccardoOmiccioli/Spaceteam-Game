package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.ControlInteraction;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Order;

import java.util.Objects;

public class OnControlInteraction implements Event {

    private ControlInteraction controlInteraction;

    public OnControlInteraction(ControlInteraction controlInteraction) {
        this.controlInteraction = controlInteraction;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = gameEngine.getLobby();
        Order currentOrder = lobby.getGame().getCurrentOrder();
        if (Objects.equals(currentOrder.getControl().getControlLabel(), controlInteraction.getControlLabel())
                && Objects.equals(currentOrder.getValue(), controlInteraction.getControlValue())) {
                    currentOrder.complete();
                    gameEngine.getMultiplayerService().publishOrderCompletion(true);
        }
    }

}
