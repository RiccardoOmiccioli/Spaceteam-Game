package it.unibo.spaceteam.model;

import it.unibo.spaceteam.utils.GridPosition;

import java.util.*;

public class ControlPanel {

    private static final int CONTROL_GRID_COLS = 3;
    private static final int CONTROL_GRID_ROWS = 3;

    private final Map<Control, GridPosition> controls;

    public ControlPanel(Map<Control, GridPosition> controls) {
        this.controls = controls;
    }

    public Map<Control, GridPosition> getControls() {
        return controls;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Control Panel: {\n");
        for (Map.Entry<Control, GridPosition> entry : controls.entrySet()) {
            Control control = entry.getKey();
            GridPosition position = entry.getValue();
            stringBuilder.append(String.format("\tControl: %s, Position: %s, Type: %s, Size: %d×%d\n", control.getControlLabel(), position, control.getControlType(), control.getControlWidth(), control.getControlHeight()));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public List<Control> getControlsAsList() {
        return controls.keySet().stream().toList();
    }

    public static ControlPanel generateControlPanel(List<Control> controlsList) {
        List<Control> controlsArrayList = new ArrayList<>(controlsList);
        Map<Control, GridPosition> controlsHashMap = new HashMap<>();
        boolean[][] occupied = new boolean[CONTROL_GRID_ROWS][CONTROL_GRID_COLS];

        Collections.shuffle(controlsArrayList);

        for (int col = 0; col < CONTROL_GRID_COLS; col++) {
            for (int row = 0; row < CONTROL_GRID_ROWS; row++) {
                for (Control control: controlsArrayList) {
                    int controlWidth = control.getControlWidth();
                    int controlHeight = control.getControlHeight();
                    if (canPlaceControl(occupied, row, col, controlWidth, controlHeight)) {
                        controlsHashMap.put(control, new GridPosition(row, col));
                        markOccupied(occupied, row, col, controlWidth, controlHeight);
                        controlsArrayList.remove(control);
                        break;
                    }
                }
            }
        }

        return new ControlPanel(controlsHashMap);
    }

    // Check if the control can fit in the grid at the specified position
    private static boolean canPlaceControl(boolean[][] occupied, int row, int col, int width, int height) {
        if (row + height > CONTROL_GRID_ROWS || col + width > CONTROL_GRID_COLS) {
            return false; // Out of bounds
        }
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                if (occupied[r][c]) {
                    return false; // Position already occupied
                }
            }
        }
        return true;
    }

    // Mark the positions occupied by the control
    private static void markOccupied(boolean[][] occupied, int row, int col, int width, int height) {
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                occupied[r][c] = true;
            }
        }
    }

}
