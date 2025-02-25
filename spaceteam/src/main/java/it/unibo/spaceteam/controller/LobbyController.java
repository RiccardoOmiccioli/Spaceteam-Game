package it.unibo.spaceteam.controller;

import it.unibo.spaceteam.game.actions.*;
import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.view.AvailableScene;

public class LobbyController extends Controller {

    public LobbyController(GameEngine gameEngine) {
        super(gameEngine);
        loadView(AvailableScene.LOBBY);
    }

    public void startMain() {
        getGameEngine().handleAction(new OpenMainMenu());
    }

    public void changeRole() {
        getGameEngine().handleAction(new ChangeRole());
    }

    public void changeStatus() {
        getGameEngine().handleAction(new ChangeStatus());
    }

    public void searchLobby(String lobbyId) {
        if (!lobbyId.isBlank()) {
            getGameEngine().handleAction(new JoinLobby(lobbyId));
        }
    }

}
