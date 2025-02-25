package it.unibo.spaceteam.game.actions;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.ControlInteraction;

public class InteractControl implements Action {

    private ControlInteraction controlInteraction;

    public InteractControl(ControlInteraction controlInteraction) {
        this.controlInteraction = controlInteraction;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        gameEngine.getMultiplayerService().publishControlInteraction(controlInteraction);
    }

}
