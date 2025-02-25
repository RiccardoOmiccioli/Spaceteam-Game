package it.unibo.spaceteam.model;

import java.util.Objects;

public class ControlInteraction {

    private final String controlLabel;
    private final String controlValue;
    private final String playerId;

    public ControlInteraction(String controlLabel, String controlValue, String playerId) {
        this.controlLabel = controlLabel;
        this.controlValue = controlValue;
        this.playerId = playerId;
    }

    public String getControlLabel() {
        return controlLabel;
    }

    public String getControlValue() {
        return controlValue;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ControlInteraction that = (ControlInteraction) object;
        return Objects.equals(controlLabel, that.controlLabel) && Objects.equals(controlValue, that.controlValue) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controlLabel, controlValue, playerId);
    }

    @Override
    public String toString() {
        return "ControlInteraction{" +
                "controlLabel='" + controlLabel + '\'' +
                ", controlValue='" + controlValue + '\'' +
                ", playerId='" + playerId + '\'' +
                '}';
    }

}
