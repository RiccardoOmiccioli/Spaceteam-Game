package it.unibo.spaceteam.view;

import it.unibo.spaceteam.controller.Controller;
import it.unibo.spaceteam.controller.LobbyController;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.Player;
import it.unibo.spaceteam.model.PlayerStatus;
import it.unibo.spaceteam.utils.fxnodes.FXHorizontalSeparator;
import it.unibo.spaceteam.utils.fxnodes.FXLabel;
import it.unibo.spaceteam.utils.fxnodes.LabelStyle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LobbyView extends View {

    public LobbyView() {}

    public void initialize(Controller controller) {
        this.setController(controller);
    }

    @Override
    protected void render(Lobby lobby) {
        lobbyId.setText(lobby.getId());
        playersCount.setText(
                (lobby.getAllPlayers().stream().filter(player -> player.getPlayerStatus() == PlayerStatus.READY || player.getPlayerStatus() == PlayerStatus.PLAYING).count() + "/" +
                (lobby.getAllPlayers().size())));
        playersList.getChildren().clear();
        addPlayer(lobby.getCurrentPlayer());
        for (Player player: lobby.getPlayers()) {
            addPlayer(player);
        }
    }

    @FXML
    public void backButtonClick() {
        ((LobbyController) this.getController()).startMain();
    }

    @FXML
    public void roleButtonClick() {
        ((LobbyController) this.getController()).changeRole();
    }

    @FXML
    public void readyButtonClick() {
        ((LobbyController) this.getController()).changeStatus();
    }

    @FXML
    public void lobbyIdInput() {
        String id = lobbyId.getText();
        if (!id.isBlank()) {
            ((LobbyController) this.getController()).searchLobby(id);
        }
    }

    @FXML
    public TextField lobbyId;

    @FXML
    public Label playersCount;

    @FXML
    public VBox playersList;

    private void addPlayer(Player player) {
        HBox playerRow = new HBox();

        Label username = new FXLabel().setText(player.getUsername()).setStyle(LabelStyle.SCREEN).build();
        Pane longSeparator = new FXHorizontalSeparator().build();
        Pane shortSeparator = new FXHorizontalSeparator().setWidth(1.0).build();
        Label role = new FXLabel().setText(player.getPlayerRole().toString()).setStyle(LabelStyle.SCREEN).build();
        Label status = new FXLabel().setText(player.getPlayerStatus().toString()).setStyle(LabelStyle.SCREEN).build();

        playerRow.getChildren().addAll(username, longSeparator, role, shortSeparator, status);
        playersList.getChildren().add(playerRow);
    }
}
