package it.unibo.spaceteam.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Screen {

    private static Stage stage;

    private static final int HEIGHT_UNITS = 81;
    private static final int WIDTH_UNITS = 47;
    private static final int BASE_FONT_SIZE = 12;

    private static final double MAX_RATIO = 1.5;
    private static final double MIN_RATIO = 0.5;

    private static double currentRatio = 1.0;
    private static int currentFrameRate = 10;

    private static double windowPositionX = 0;
    private static double windowPositionY = 0;

    public static void initializeScreen(Stage stage) {
        Screen.stage = stage;
        stage.setTitle("Spaceteam");
        stage.setResizable(false);
        stage.setX(windowPositionX);
        stage.setY(windowPositionY);
        stage.show();

        stage.setOnHidden(e -> {
            windowPositionX = stage.getX();
            windowPositionY = stage.getY();
        });

        stage.setOnCloseRequest(event -> System.exit(0));
    }

    public static Stage getStage() {
        if (stage != null) {
            return stage;
        }
        throw new IllegalStateException("View not initialized");
    }

    public static View loadScene(AvailableScene selectedScene) {
        FXMLLoader loader = new FXMLLoader();
        switch (selectedScene) {
            case MAIN -> loader.setLocation(ClassLoader.getSystemResource("fxml/MainMenu.fxml"));
            case LOBBY -> loader.setLocation(ClassLoader.getSystemResource("fxml/LobbyMenu.fxml"));
            case GAME -> loader.setLocation(ClassLoader.getSystemResource("fxml/GameMenu.fxml"));
        }
        try {
            Parent root = loader.load();
            Platform.runLater(() -> {
                getStage().hide();
                Scene scene = new Scene(root);
                getStage().setScene(scene);
                getStage().show();
                resize(currentRatio);
            });
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getBaseFontSize() {
        return BASE_FONT_SIZE;
    }

    public static double getCurrentRatio() {
        return currentRatio;
    }

    public static int getCurrentFrameRate() {
        return currentFrameRate;
    }

    public static void resize(Double ratio) {
        currentRatio = ratio;
        getStage().setWidth(WIDTH_UNITS * BASE_FONT_SIZE * ratio);
        getStage().setHeight(HEIGHT_UNITS * BASE_FONT_SIZE * ratio);
        getStage().getScene().getRoot().setStyle("-fx-font-size: " + BASE_FONT_SIZE * ratio + "px;");
    }

    public static double interpolateRatio(Double value, Double min, Double max) {
        return MIN_RATIO + ((value - min) / (max - min)) * (MAX_RATIO - MIN_RATIO);
    }

    public static void framerate(int framerateOption) {
        switch (framerateOption) {
            case 1 -> currentFrameRate = 1;
            case 2 -> currentFrameRate = 10;
            case 3 -> currentFrameRate = 30;
            default -> throw new IllegalArgumentException("Invalid framerate option");
        }
    }

}
