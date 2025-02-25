package it.unibo.spaceteam.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Lobby {

    private final String id;
    private final Player currentPlayer;
    private List<Player> players;
    private Game game;

    public Lobby(Player currentPlayer, String lobbyId) {
        if (lobbyId == null) {
            String longId = UUID.randomUUID().toString();
            this.id = longId.substring(longId.length() - 6);
        } else {
            this.id = lobbyId;
        }
        this.currentPlayer = currentPlayer;
        this.players = new ArrayList<>();
        this.game = new Game();
    }

    public String getId() {
        return id;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getAllPlayers() {
        return Stream.concat(players.stream(), Stream.of(currentPlayer)).toList();
    }

    public Player findPlayer(String id) {
        return getAllPlayers().stream().filter(player -> player.getId().equals(id)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public Player findPlayer(Player player) {
        return getAllPlayers().stream().filter(p -> p.equals(player)).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public List<Player> filter(Predicate<? super Player> predicate) {
        return getAllPlayers().stream().filter(predicate).toList();
    }

    public boolean allMatch(Predicate<? super Player> predicate) {
        return getAllPlayers().stream().allMatch(predicate);
    }

    public boolean anyMatch(Predicate<? super Player> predicate) {
        return getAllPlayers().stream().anyMatch(predicate);
    }

    public void addPlayer(Player player) {
        if (player != currentPlayer) {
            this.players.add(player);
            return;
        }
        throw new IllegalStateException();
    }

    public void removePlayer(Player player) {
        if (player != currentPlayer) {
            this.players.remove(player);
            return;
        }
        throw new IllegalStateException();
    }

    public void updatePlayer(Player player) {
        if (!player.equals(currentPlayer)) {
            if (players.contains(player)) {
                findPlayer(player).setPlayerRole(player.getPlayerRole());
                findPlayer(player).setPlayerStatus(player.getPlayerStatus());
            } else {
                addPlayer(player);
            }
        }
    }

    public Game getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return Objects.equals(id, lobby.id) && Objects.equals(currentPlayer, lobby.currentPlayer) && Objects.equals(players, lobby.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currentPlayer, players);
    }

}
