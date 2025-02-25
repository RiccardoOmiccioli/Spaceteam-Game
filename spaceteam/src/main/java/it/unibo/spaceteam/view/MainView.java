package it.unibo.spaceteam.view;

import com.google.gson.Gson;
import it.unibo.spaceteam.controller.Controller;
import it.unibo.spaceteam.controller.MainController;
import it.unibo.spaceteam.model.Control;
import it.unibo.spaceteam.model.ControlPanel;
import it.unibo.spaceteam.model.ControlType;
import it.unibo.spaceteam.model.Lobby;
import it.unibo.spaceteam.utils.fxnodes.FXButton;
import it.unibo.spaceteam.utils.fxnodes.FXLabel;
import it.unibo.spaceteam.utils.fxnodes.FXToggleButton;
import it.unibo.spaceteam.utils.fxnodes.LabelStyle;
import it.unibo.spaceteam.utils.json.GsonUtils;
import it.unibo.spaceteam.utils.json.JsonReader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class MainView extends View {

    public MainView() {
        Font.loadFont(String.valueOf(ClassLoader.getSystemResource("font/Graph-35-pix.ttf")).replaceAll("%20", " "), 12);
        Font.loadFont(String.valueOf(ClassLoader.getSystemResource("font/ShareTechMono-Regular.ttf")).replaceAll("%20", " "), 12);
    }

    @Override
    public void initialize(Controller controller) {
        this.setController(controller);
        sizeSlider.setValue(Screen.getCurrentRatio() * (sizeSlider.getMin() + (sizeSlider.getMax() - sizeSlider.getMin()) / 2));
    }

    private void createControlUI(Control control, GridPane controlsGrid, int row, int col) {
        StackPane controlPane = (StackPane) getCell(controlsGrid, row, col);
        VBox controlVBox = (VBox) controlPane.getChildren().getFirst();
        controlVBox.getChildren().clear();

        Label controlLabel = new FXLabel().setText(control.getControlLabel()).setStyle(LabelStyle.CONTROL).build();
        controlVBox.getChildren().add(controlLabel);

        StackPane controlButton;
        if (control.getControlType() == ControlType.TOGGLE_BUTTON) {
            controlButton = new FXToggleButton().setText(control.getControlValues()).build();
        } else {
            controlButton = new FXButton().setText(control.getControlValues().getFirst()).build();
        }

        controlVBox.getChildren().add(controlButton);
    }

    @Override
    protected void render(Lobby lobby) {
        Gson gson = GsonUtils.createGson();
        List<Control> filteredControls = gson.fromJson(JsonReader.readJsonFile("controls.json"), ControlPanel.class).getControlsAsList().stream()
                .filter(control -> (control.getControlType() == ControlType.PUSH_BUTTON || control.getControlType() == ControlType.TOGGLE_BUTTON)
                        && control.getControlWidth() == 1 && control.getControlHeight() == 1)
                .limit(3)
                .toList();

        createControlUI(filteredControls.get(0), controlsGrid, 0, 0);
        createControlUI(filteredControls.get(1), controlsGrid, 0, 2);
        createControlUI(filteredControls.get(2), controlsGrid, 2, 2);
    }

    @FXML
    public VBox mainPane;

    @FXML
    public TextField usernameTextField;

    @FXML
    public Slider sizeSlider;

    @FXML
    public Slider frameRateSlider;

    @FXML
    public void startLobbySearch() {
        ((MainController) this.getController()).startLobby(usernameTextField.getText());
    }

    @FXML
    public void quit() {
        ((MainController) this.getController()).quitGame();
    }

    @FXML
    public void sizeChanged() {
        Screen.resize(Screen.interpolateRatio(sizeSlider.getValue(), sizeSlider.getMin(), sizeSlider.getMax()));
    }

    @FXML
    public void frameRateChanged() {
        Screen.framerate((int) frameRateSlider.getValue());
    }

    @FXML
    public GridPane controlsGrid;

}
