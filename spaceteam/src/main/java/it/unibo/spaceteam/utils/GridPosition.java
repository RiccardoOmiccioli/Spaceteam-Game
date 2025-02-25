package it.unibo.spaceteam.utils;

public class GridPosition {

    private final int row;
    private final int col;

    public GridPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return String.format("%d-%d", row, col);
    }

}
