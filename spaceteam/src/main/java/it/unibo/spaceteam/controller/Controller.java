package it.unibo.spaceteam.controller;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.view.AvailableScene;
import it.unibo.spaceteam.view.Screen;
import it.unibo.spaceteam.view.View;

public abstract class Controller {

    private View view;

    private final GameEngine gameEngine;

    public Controller(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void loadView(AvailableScene scene) {
        view = Screen.loadScene(scene);
        view.initialize(this);
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    // Call a GUI update based on the current state
    public void update(Lobby lobby) {
        this.view.renderAsync(lobby);
    }

}
