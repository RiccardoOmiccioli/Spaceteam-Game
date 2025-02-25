package it.unibo.spaceteam.utils.fxnodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FXButton {

    private String text;

    public FXButton() {
        this.text = "";
    }

    public FXButton setText(String text) {
        this.text = text;
        return this;
    }

    public StackPane build() {
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("button-container");

        Pane pane = new Pane();
        pane.getStyleClass().add("button-background");

        Button button = new Button();
        button.getStyleClass().add("button");
        button.setEllipsisString(".");

        VBox vbox = new VBox();
        vbox.getStyleClass().add("button-label-container");

        Label label = new FXLabel().setText(text).setStyle(LabelStyle.BUTTON).build();

        vbox.getChildren().add(label);
        button.setGraphic(vbox);

        stackPane.getChildren().add(0, pane);
        stackPane.getChildren().add(1, button);

        return stackPane;
    }

    public static void setHandler(StackPane fxButton, EventHandler<ActionEvent> event) {
        ((Button) fxButton.getChildren().get(1)).setOnAction(event);
    }

}
