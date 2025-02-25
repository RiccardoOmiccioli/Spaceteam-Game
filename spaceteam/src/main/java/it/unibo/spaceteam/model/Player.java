package it.unibo.spaceteam.model;

import java.util.Objects;
import java.util.UUID;


public class Player {

    private String id;
    private String username;
    private PlayerRole playerRole;
    private PlayerStatus playerStatus;
    private ControlPanel controlPanel;

    public Player() {
        this.id = UUID.randomUUID().toString();
        this.username = "";
        this.playerRole = PlayerRole.CAPTAIN;
        this.playerStatus = PlayerStatus.ONLINE;
    }

    public Player(String username) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.playerRole = PlayerRole.CAPTAIN;
        this.playerStatus = PlayerStatus.ONLINE;
    }

    public Player(String username, String id) {
        this.id = id;
        this.username = username;
        this.playerRole = PlayerRole.CAPTAIN;
        this.playerStatus = PlayerStatus.ONLINE;
    }

    public Player(String id, String username, PlayerRole playerRole, PlayerStatus playerStatus) {
        this.id = id;
        this.username = username;
        this.playerRole = playerRole;
        this.playerStatus = playerStatus;
    }

    public Player(String id, String username, PlayerRole playerRole, PlayerStatus playerStatus, ControlPanel controlPanel) {
        this.id = id;
        this.username = username;
        this.playerRole = playerRole;
        this.playerStatus = playerStatus;
        this.controlPanel = controlPanel;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerStatus=" + playerStatus +
                ", playerRole=" + playerRole +
                ", username='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
