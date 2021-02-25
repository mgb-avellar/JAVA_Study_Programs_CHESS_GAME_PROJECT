package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
        /*
        This class extends Piece, so we initialize with super
         */
    }

    public Color getColor() {
        return color;
    }
}
