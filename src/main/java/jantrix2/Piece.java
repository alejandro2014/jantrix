package jantrix2;

import java.util.List;

public abstract class Piece {
    private int row;
    private int column;
    private int turnNo = 0;

    private List<Integer[][]> turnsPositions;

    public void setTurnsPositions(List<Integer[][]> turnsPositions) {
        this.turnsPositions = turnsPositions;
    }

    public void turn() {
        if (this.isTurnPossible()) {
            turnNo++;

            if (turnNo >= turnsPositions.size()) {
                turnNo = 0;
            }
        }
    }

    public boolean isTurnPossible() {
        return true;
    }
}
