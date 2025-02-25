package it.unibo.spaceteam.controller;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.game.actions.InteractControl;
import it.unibo.spaceteam.model.ControlInteraction;
import it.unibo.spaceteam.view.AvailableScene;

public class GameController extends Controller {

    public GameController(GameEngine gameEngine) {
        super(gameEngine);
        loadView(AvailableScene.GAME);
    }

    public void handleControlInteraction(String controlLabel, String controlValue) {
        getGameEngine().handleAction(new InteractControl(new ControlInteraction(controlLabel, controlValue, getGameEngine().getLobby().getCurrentPlayer().getId())));
    }

}
