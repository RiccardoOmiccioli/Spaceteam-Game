package it.unibo.spaceteam;

import it.unibo.spaceteam.game.GameEngine;
import it.unibo.spaceteam.game.actions.OpenMainMenu;
import it.unibo.spaceteam.view.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Screen.initializeScreen(stage);
        new GameEngine().handleAction(new OpenMainMenu());

    }

}
