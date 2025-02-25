package it.unibo.spaceteam.model;

public enum PlayerRole {

    CREW_MEMBER("CREW"),
    CAPTAIN("CAPT");

    private final String name;

    PlayerRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
