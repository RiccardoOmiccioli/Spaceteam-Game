package it.unibo.spaceteam.model;

public class Level {

    private final int level;
    private int completedOrders;
    private final int requiredOrders;
    private int currentIntegrity;


    public Level(int level) {
        this.level = level;
        this.completedOrders = 0;
        this.requiredOrders = 2 + level;
        this.currentIntegrity = 100;
    }

    public int getLevel() {
        return level;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public int getRequiredOrders() {
        return requiredOrders;
    }

    public int getCurrentIntegrity() {
        return currentIntegrity;
    }

    public Level nextLevel() {
        return new Level(level + 1);
    }

    public void completeOrder() {
        completedOrders = completedOrders + 1;
    }

    public void takeDamage() {
        currentIntegrity = Math.max(0, currentIntegrity - 5 * level);
    }

    public boolean isLevelCompleted() {
        return completedOrders >= requiredOrders;
    }

    public boolean isIntegrityCompromised() {
        return currentIntegrity <= 0;
    }

}
