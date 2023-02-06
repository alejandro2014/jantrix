package jantrix2;

import java.util.ArrayList;
import java.util.List;

public class SquarePiece extends Piece {
    public SquarePiece() {
        List<Integer[][]> turnsPositions = new ArrayList<>();

        turnsPositions.add(new Integer[][] {{0, 0}, {1, 0}, {0, -1}, {1, -1}});

        this.setTurnsPositions(turnsPositions);
    }
}
