package jantrix2;

import java.util.ArrayList;
import java.util.List;

public class LongPiece extends Piece {
    public LongPiece() {
        List<Integer[][]> turnsPositions = new ArrayList<>();

        turnsPositions.add(new Integer[][] {{0, 0}, {0, -1}, {0, -2}, {0, -3}});
        turnsPositions.add(new Integer[][] {{-1, 0}, {0, 0}, {1, 0}, {2, 0}});

        this.setTurnsPositions(turnsPositions);
    }
}
