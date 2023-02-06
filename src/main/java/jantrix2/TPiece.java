package jantrix2;

import java.util.ArrayList;
import java.util.List;

public class TPiece extends Piece {
    public TPiece() {
        List<Integer[][]> turnsPositions = new ArrayList<>();

        turnsPositions.add(new Integer[][] {{-1, 0}, {0, 0}, {1, 0}, {0, -1}});
        turnsPositions.add(new Integer[][] {{0, 0}, {0, -1}, {1, -1}, {0, -2}});
        turnsPositions.add(new Integer[][] {{0, 0}, {-1, -1}, {0, -1}, {1, -1}});
        turnsPositions.add(new Integer[][] {{0, 0}, {-1, -1}, {-1, 0}, {-2, 0}});

        this.setTurnsPositions(turnsPositions);
    }
}
