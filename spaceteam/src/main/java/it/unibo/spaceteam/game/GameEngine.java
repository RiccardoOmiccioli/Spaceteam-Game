package it.unibo.spaceteam.game;

import it.unibo.spaceteam.controller.Controller;
import it.unibo.spaceteam.distributed.MultiplayerService;
import it.unibo.spaceteam.game.actions.Action;
import it.unibo.spaceteam.game.events.Event;
import it.unibo.spaceteam.model.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameEngine {

    private Controller controller;
    private MultiplayerService multiplayerService;
    private Lobby lobby;

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public GameEngine() {
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public MultiplayerService getMultiplayerService() {
        return multiplayerService;
    }

    public void setMultiplayerService(MultiplayerService multiplayerService) {
        this.multiplayerService = multiplayerService;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void handleAction(Action action) {
        executorService.submit(() -> action.execute(this));
    }

    public void handleEvent(Event event) {
        executorService.submit(() -> event.execute(this));
    }

}
