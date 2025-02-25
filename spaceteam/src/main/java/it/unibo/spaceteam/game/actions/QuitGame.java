package it.unibo.spaceteam.game.actions;

import it.unibo.spaceteam.game.GameEngine;

public class QuitGame implements  Action {

    @Override
    public void execute(GameEngine gameEngine) {
        System.exit(0);
    }

}
