package jantrix2;

import java.util.ArrayList;
import java.util.List;

public class LPiece extends Piece {
    public LPiece() {
        List<Integer[][]> turnsPositions = new ArrayList<>();

        turnsPositions.add(new Integer[][] {{0, 0}, {1, 0}, {0, -1}, {0, -2}});
        turnsPositions.add(new Integer[][] {{0, 0}, {0, -1}, {-1, -1}, {2, -1}});
        turnsPositions.add(new Integer[][] {{0, 0}, {0, -1}, {-1, -2}, {0, -2}});
        turnsPositions.add(new Integer[][] {{0, 0}, {1, 0}, {2, 0}, {2, -1}});

        this.setTurnsPositions(turnsPositions);
    }
}
