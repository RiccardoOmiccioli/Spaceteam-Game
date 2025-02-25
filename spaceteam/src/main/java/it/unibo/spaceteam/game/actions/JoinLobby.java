package it.unibo.spaceteam.game.actions;

import com.google.gson.Gson;
import it.unibo.spaceteam.controller.LobbyController;
import it.unibo.spaceteam.distributed.MultiplayerService;
import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.model.Control;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.utils.json.GsonUtils;
import it.unibo.spaceteam.utils.json.JsonReader;

import java.util.List;

public class JoinLobby implements Action {

    private String lobbyId;

    public JoinLobby(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    @Override
    public void execute(GameEngine gameEngine) {
        Lobby lobby = new Lobby(gameEngine.getLobby().getCurrentPlayer(), lobbyId);

        Gson gson = GsonUtils.createGson();
        List<Control> allControls = gson.fromJson(JsonReader.readJsonFile("controls.json"), ControlPanel.class).getControlsAsList();
        lobby.getCurrentPlayer().setControlPanel(ControlPanel.generateControlPanel(allControls));

        gameEngine.setLobby(lobby);

        gameEngine.setController(new LobbyController(gameEngine));
        gameEngine.getController().update(lobby);

        gameEngine.getMultiplayerService().leaveLobby();

        gameEngine.setMultiplayerService(new MultiplayerService(gameEngine));

        gameEngine.getMultiplayerService().subscribeLevelStart();

        gameEngine.getMultiplayerService().subscribePlayerUpdate();
        gameEngine.getMultiplayerService().publishPlayerUpdate();
    }

}
