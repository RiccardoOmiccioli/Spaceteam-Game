package it.unibo.spaceteam.controller;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.game.actions.QuitGame;
import it.unibo.spaceteam.game.actions.StartLobbySearch;
import it.unibo.spaceteam.model.Player;
import it.unibo.spaceteam.view.AvailableScene;

public class MainController extends Controller {

    public MainController(GameEngine gameEngine) {
        super(gameEngine);
        loadView(AvailableScene.MAIN);
        this.update(gameEngine.getLobby());
    }

    public void startLobby(String username) {
        if (!username.isBlank()) {
            getGameEngine().handleAction(new StartLobbySearch(new Player(username)));
        }
    }

    public void quitGame() {
        getGameEngine().handleAction(new QuitGame());
    }

}
