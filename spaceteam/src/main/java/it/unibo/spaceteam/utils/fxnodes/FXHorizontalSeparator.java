package it.unibo.spaceteam.utils.fxnodes;

import it.unibo.spaceteam.view.Screen;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class FXHorizontalSeparator {
    private double prefWidth;
    private final double prefHeight;

    public FXHorizontalSeparator() {
        this.prefWidth = -1;
        this.prefHeight = -1;
    }

    // Set the horizontal separator width in em units
    public FXHorizontalSeparator setWidth(Double prefWidth) {
        this.prefWidth = prefWidth;
        return this;
    }

    public Pane build() {
        Pane panel = new Pane();
        if (prefWidth == -1) {
            HBox.setHgrow(panel, Priority.ALWAYS);
        } else if (prefWidth > 0) {
            panel.setPrefWidth(prefWidth * Screen.getBaseFontSize());
            panel.setPrefHeight(prefHeight * Screen.getBaseFontSize());
        } else {
            throw new IllegalStateException();
        }
        return panel;
    }

}
