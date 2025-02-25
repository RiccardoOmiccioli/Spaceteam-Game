package it.unibo.spaceteam.model;

import java.util.List;

public class Control {

    private final ControlType controlType;
    private final String controlLabel;
    private final List<String> controlValues;
    private final int controlWidth;
    private final int controlHeight;

    public Control(ControlType controlType, String controlLabel, List<String> controlValues, int controlWidth, int controlHeight) {
        this.controlType = controlType;
        this.controlLabel = controlLabel;
        this.controlValues = controlValues;
        this.controlWidth = controlWidth;
        this.controlHeight = controlHeight;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public String getControlLabel() {
        return controlLabel;
    }

    public List<String> getControlValues() {
        return controlValues;
    }

    public int getControlWidth() {
        return controlWidth;
    }

    public int getControlHeight() {
        return controlHeight;
    }

    @Override
    public String toString() {
        return "Control{" +
                "controlType=" + controlType +
                ", controlLabel='" + controlLabel + '\'' +
                ", controlValues=" + controlValues +
                ", controlWidth=" + controlWidth +
                ", controlHeight=" + controlHeight +
                '}';
    }

}
