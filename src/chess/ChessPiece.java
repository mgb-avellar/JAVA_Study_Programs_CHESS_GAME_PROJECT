package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

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

    protected boolean isThereOpponentPiece(Position position) {

        // We need to know if there is an adversary piece in the position I want to move my piece to
        ChessPiece p = (ChessPiece) getBoard().piece(position);

        return p != null && p.getColor() != color;
    }
}
