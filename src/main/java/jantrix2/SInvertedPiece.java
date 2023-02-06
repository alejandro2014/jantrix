package jantrix2;

import java.util.ArrayList;
import java.util.List;

public class SInvertedPiece extends Piece {
    public SInvertedPiece() {
        List<Integer[][]> turnsPositions = new ArrayList<>();

        turnsPositions.add(new Integer[][] {{0, 0}, {0, -1}, {1, -1}, {1, -2}});
        turnsPositions.add(new Integer[][] {{0, 0}, {1, 0}, {-1, -1}, {0, -1}});

        this.setTurnsPositions(turnsPositions);
    }
}
