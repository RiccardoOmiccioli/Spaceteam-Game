package it.unibo.spaceteam.game.events;

import it.unibo.spaceteam.game.GameEngine;

public interface Event {

    void execute(GameEngine gameEngine);

}
