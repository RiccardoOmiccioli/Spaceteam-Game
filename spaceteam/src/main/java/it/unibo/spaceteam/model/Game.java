package it.unibo.spaceteam.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {

    private Order currentOrder;
    private final List<Level> levels;
    private boolean isEnded;

    public Game() {
        levels = new ArrayList<>();
        levels.add(new Level(0));
        this.isEnded = false;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getCurrentLevel() {
        return levels.getLast();
    }

    public boolean isEnded() {
        return isEnded;
    }

    public Order issueNewOrder(Lobby lobby) {
        List<Control> availableControls = lobby.getAllPlayers().stream()
                .filter(player -> player.getPlayerStatus() == PlayerStatus.PLAYING)
                .map(Player::getControlPanel)
                .map(ControlPanel::getControlsAsList)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        Control control = availableControls.get(new Random().nextInt(availableControls.size()));
        String value = control.getControlValues().get(new Random().nextInt(control.getControlValues().size()));
        long orderMaxTime = Math.max(5000, 20000 - 1000 * getCurrentLevel().getLevel() + 1000);
        currentOrder = new Order(control, value, orderMaxTime);
        return currentOrder;
    }

    public void startNewLevel(int levelNumber) {
        levels.add(new Level(levelNumber));
    }

    public void endGame() {
        if (currentOrder != null) {
            currentOrder.complete();
        }
        isEnded = true;
    }

}
