package it.unibo.spaceteam.view;

import it.unibo.spaceteam.controller.Controller;
import it.unibo.spaceteam.model.Lobby;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public abstract class View {

    private Controller controller;

    // This method should be used to set the controller and initialize view elements if necessary
    public abstract void initialize(Controller controller);

    public Controller getController() {
        return this.controller;
    };

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void renderAsync(Lobby lobby) {
        Platform.runLater(() -> render(lobby));
    }

    protected abstract void render(Lobby lobby);

    public Node getCell(GridPane grid, int row, int col) {
        for (Node node : grid.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            Integer nodeColIndex = GridPane.getColumnIndex(node);
            if (nodeRowIndex == null) {
                nodeRowIndex = 0;
            }
            if (nodeColIndex == null) {
                nodeColIndex = 0;
            }
            if (nodeRowIndex == row && nodeColIndex == col) {
                return node;
            }
        }
        return null;
    }

}
