package it.unibo.spaceteam.utils.fxnodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;

import java.util.Collections;
import java.util.List;

public class FXToggleButton {

    private List<String> text;
    private ToggleButton toggleButton;

    public FXToggleButton() {
        this.text = Collections.singletonList("");
    }

    public FXToggleButton setText(List<String> text) {
        this.text = text;
        return this;
    }

    public StackPane build() {
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("button-container");

        Pane pane = new Pane();
        pane.getStyleClass().add("button-background");
        pane.prefWidthProperty().bind(stackPane.widthProperty());

        toggleButton = new ToggleButton();
        toggleButton.getStyleClass().add("button");
        toggleButton.setEllipsisString(".");
        toggleButton.prefWidthProperty().bind(stackPane.widthProperty());

        VBox vbox = new VBox();
        vbox.getStyleClass().add("button-label-container");

        for (String textItem : text) {
            Label label = new FXLabel().setText(textItem).setStyle(LabelStyle.BUTTON).build();
            vbox.getChildren().add(label);
        }

        toggleButton.setGraphic(vbox);
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                vbox.getChildren().getFirst().getStyleClass().add("button-label-selected");
                vbox.getChildren().getLast().getStyleClass().remove("button-label-selected");
            } else {
                vbox.getChildren().getFirst().getStyleClass().remove("button-label-selected");
                vbox.getChildren().getLast().getStyleClass().add("button-label-selected");
            }
        });

        stackPane.getChildren().add(0, pane);
        stackPane.getChildren().add(1, toggleButton);

        return stackPane;
    }

    public static void setHandler(StackPane fxToggleButton, EventHandler<ActionEvent> event) {
        ((ToggleButton) fxToggleButton.getChildren().get(1)).setOnAction(event);
    }

    public static boolean isSelected(StackPane fxToggleButton) {
        return ((ToggleButton) fxToggleButton.getChildren().get(1)).isSelected();
    }

}
