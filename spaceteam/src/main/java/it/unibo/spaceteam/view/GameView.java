package it.unibo.spaceteam.view;

import it.unibo.spaceteam.controller.Controller;
import it.unibo.spaceteam.controller.GameController;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.model.PlayerRole;
import it.unibo.spaceteam.model.PlayerStatus;
import it.unibo.spaceteam.utils.fxnodes.*;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GameView extends View {

    public GameView() {}

    public void initialize(Controller controller) {
        this.setController(controller);
        Lobby lobby = controller.getGameEngine().getLobby();
        ControlPanel controlPanel = lobby.getCurrentPlayer().getControlPanel();
        controlPanel.getControls().forEach((control, gridPosition) -> {
            StackPane stackPane = (StackPane) getCell(controlsGrid, gridPosition.getRow(), gridPosition.getCol());
            VBox vBox = (VBox) stackPane.getChildren().getFirst();
            vBox.getChildren().clear();
            Label label = new FXLabel().setText(control.getControlLabel()).setStyle(LabelStyle.CONTROL).build();
            vBox.getChildren().add(label);
            switch (control.getControlType()) {
                case PUSH_BUTTON -> {
                    StackPane button = new FXButton().setText(control.getControlValues().getFirst()).build();
                    FXButton.setHandler(button, event -> {
                        ((GameController) this.getController()).handleControlInteraction(control.getControlLabel(), control.getControlValues().getFirst());
                    });
                    vBox.getChildren().add(button);
                }
                case TOGGLE_BUTTON -> {
                    StackPane toggleButton = new FXToggleButton().setText(control.getControlValues()).build();
                    FXToggleButton.setHandler(toggleButton, event -> {
                        ((GameController) this.getController()).handleControlInteraction(control.getControlLabel(), FXToggleButton.isSelected(toggleButton) ? control.getControlValues().getFirst() : control.getControlValues().getLast());
                    });
                    vBox.getChildren().add(toggleButton);
                }
                case SLIDER -> {
                    Slider slider = new FXSlider()
                            .setText(control.getControlValues())
                            .setOrientation(control.getControlHeight() > control.getControlWidth() ? Orientation.VERTICAL : Orientation.HORIZONTAL)
                            .build();
                    FXSlider.setHandler(slider, event -> {
                        ((GameController) this.getController()).handleControlInteraction(control.getControlLabel(), control.getControlValues().get((int) slider.getValue() - 1));
                    });
                    vBox.getChildren().add(slider);
                }
            }
            // Set control width and height in grid layout, hide adjacent panels if necessary
            GridPane.setRowSpan(stackPane, control.getControlHeight());
            GridPane.setColumnSpan(stackPane, control.getControlWidth());
            int startRow = gridPosition.getRow();
            int startCol = gridPosition.getCol();
            int endRow = startRow + control.getControlHeight();
            int endCol = startCol + control.getControlWidth();
            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    if (i == startRow && j == startCol) {
                        continue;
                    }
                    StackPane adjacentPanel = (StackPane) getCell(controlsGrid, i, j);
                    adjacentPanel.setOpacity(0);
                    adjacentPanel.setMouseTransparent(true);
                }
            }
        });

    }

    @Override
    protected void render(Lobby lobby) {
        playersCount.setText(String.valueOf(lobby.getAllPlayers().stream().filter(player -> player.getPlayerStatus() == PlayerStatus.PLAYING).toList().size()));
        if (lobby.getGame().isEnded()) {
            currentOrder.setText("> GAME OVER\n  DISCONNECTING");
        } else {
            if (lobby.getGame().getCurrentOrder() != null) {
                timerProgress.setProgress((double) lobby.getGame().getCurrentOrder().getCurrentTime() / lobby.getGame().getCurrentOrder().getMaxTime());
                currentOrder.setText("> " + lobby.getGame().getCurrentOrder().getControl().getControlLabel() + "\n  " + lobby.getGame().getCurrentOrder().getValue());
            }
            if (lobby.getCurrentPlayer().getPlayerRole() == PlayerRole.CREW_MEMBER) {
                timerProgress.setPrefHeight(0);
                timerProgress.setVisible(false);
                if (lobby.getCurrentPlayer().getPlayerStatus() == PlayerStatus.PLAYING) {
                    currentOrder.setText("> EXECUTE ORDERS");
                }
            }
        }
        currentLevel.setText(String.valueOf(lobby.getGame().getCurrentLevel().getLevel()));
        currentIntegrity.setText(String.valueOf(lobby.getGame().getCurrentLevel().getCurrentIntegrity()));
        completedOrder.setText(String.valueOf(lobby.getGame().getCurrentLevel().getCompletedOrders()));
        totalOrder.setText(String.valueOf(lobby.getGame().getCurrentLevel().getRequiredOrders()));
    }

    @FXML
    public GridPane controlsGrid;

    @FXML
    public ProgressBar timerProgress;

    @FXML
    public Label playersCount;

    @FXML
    public Label currentOrder;

    @FXML
    public Label completedOrder;

    @FXML
    public Label totalOrder;

    @FXML
    public Label currentLevel;

    @FXML
    public Label currentIntegrity;

}
