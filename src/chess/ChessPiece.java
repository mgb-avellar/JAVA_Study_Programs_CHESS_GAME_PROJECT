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

    /*
        In order to implement the check logic, I need to create a getter for the chessPosition and test
        if my King can move or not to this position and to see if there is a adversary piece threatening
        my King.
     */

    public ChessPosition getChessPosition () {
        return ChessPosition.fromPosition(position);
        /*
         Recalling that this 'position' variable if from the Piece class in the boardgame layer
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
