package jantrix2;

public class PieceFactory {
    public static Piece getPiece(int pieceType) {
        return switch (pieceType) {
            case 0 -> new LongPiece();
            case 1 -> new TPiece();
            case 2 -> new SquarePiece();
            case 3 -> new LInvertedPiece();
            case 4 -> new LPiece();
            case 5 -> new SInvertedPiece();
            case 6 -> new SPiece();
            default -> null;
        };
    }
}
