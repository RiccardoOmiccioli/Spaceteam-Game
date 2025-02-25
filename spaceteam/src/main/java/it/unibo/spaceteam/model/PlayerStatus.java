package it.unibo.spaceteam.model;

public enum PlayerStatus {

    OFFLINE("OFF"),
    ONLINE("ACT"),
    READY("RDY"),
    PLAYING("PLY");

    private final String name;

    PlayerStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
