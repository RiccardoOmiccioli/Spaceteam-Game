package it.unibo.spaceteam.utils.fxnodes;

import javafx.scene.control.Label;

public class FXLabel {

    private String text;
    private String styleClass;

    public FXLabel() {
        this.text = "";
        this.styleClass = "";
    }

    public FXLabel setText(String text) {
        this.text = text;
        return this;
    }

    public FXLabel setStyle(LabelStyle style) {
        this.styleClass = style.getStyleClass();
        return this;
    }

    public Label build() {
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        label.setEllipsisString(".");
        return label;
    }

}
