package it.unibo.spaceteam.utils.fxnodes;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Collections;
import java.util.List;

public class FXSlider {

    private List<String> text;
    private Orientation orientation;

    public FXSlider() {
        this.text = Collections.singletonList("");
    }

    public FXSlider setText(List<String> text) {
        this.text = text;
        return this;
    }

    public FXSlider setOrientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }

    public Slider build() {
        Slider slider = new Slider();
        slider.getStyleClass().add("snap-slider");
        slider.setMin(1);
        slider.setMax(text.size());
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setOrientation(orientation);
        VBox.setVgrow(slider, Priority.ALWAYS);
        HBox.setHgrow(slider, Priority.ALWAYS);
        slider.setLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Double value) {
                if (value <= text.size()) {
                    return text.get(value.intValue() - 1);
                } else {
                    return String.valueOf(value.intValue());
                }

            }
            @Override
            public Double fromString(String string) {
                return null;
            }
        });
        return slider;
    }

    public static void setHandler(Slider slider, EventHandler<? super MouseEvent> event) {
        slider.setOnMouseReleased(event);
    }

}
