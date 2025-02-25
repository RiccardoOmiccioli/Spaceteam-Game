package it.unibo.spaceteam.utils.fxnodes;

public enum LabelStyle {
    SCREEN("screen-text"),
    CONTROL("control-label"),
    BUTTON("button-label");

    private final String styleClass;

    LabelStyle(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
