package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    // Esta é a classe que representa a peça Rei

    /*
    In order to implement the Castling special move, the King must have access to the ChessMatch class
    for then we can make the King understand the overrall state of the match, the possible moves and the
    conditions to realize this movement.
    I also need to implement a method to test the Castling conditions.
     */

    private ChessMatch chessMatch; // this is how we associate the classes

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
        /*
         Faço isso para que seja impresso um K no tabuleiro na posição em que
         o rei está
         */
    }

    /*
        For implementing the King moves, I will create a method called canMove which will tell me
        if it is possible to the king to move in a certain direction.
     */

    private boolean canMove(Position position) {
        ChessPiece auxP = (ChessPiece) getBoard().piece(position);
        return auxP == null || auxP.getColor() != getColor();
    }

    /*
    The Castling conditions
     */

    private boolean testRookCastling(Position position) {

        // Is there a Rook in this position? If so, there are castling conditions?

        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];

        Position auxP = new Position(0,0);

        // above

        auxP.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // below

        auxP.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // left

        auxP.setValues(position.getRow() , position.getColumn() - 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // right

        auxP.setValues(position.getRow() , position.getColumn() + 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // northwest

        auxP.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // northeast

        auxP.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // southwest

        auxP.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // southeast

        auxP.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxP) && canMove(auxP)) {

            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        /*
         Special move Castling: this is also a possible move, but a set of possibilities must
         be fulfilled to realize this movement
         */

        if (getMoveCount() == 0 && !chessMatch.getCheck()) { // The King can not be in check and can not have made any movement.

            /*
             We also need to check if there is two vacant squares to the left and right of the King
             and if the Rooks have not moved yet.
             */

            // First, the king-side castling (to the right)

            Position posRook1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posRook1)) {
                // Here, the two positions to the right of the King
                Position position1 = new Position(position.getRow(), position.getColumn() + 1);
                Position position2 = new Position(position.getRow(), position.getColumn() + 2);

                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null ){
                    // Now I must include this possibility to the matrix f possible movements of the King

                    matrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }

            // Now, the queen-side castling (to the left)

            // First, the king-side castling (to the right)

            Position posRook2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posRook1)) {
                // Here, the three positions to the left of the King
                Position position1 = new Position(position.getRow(), position.getColumn() - 1);
                Position position2 = new Position(position.getRow(), position.getColumn() - 2);
                Position position3 = new Position(position.getRow(), position.getColumn() - 3);

                if (getBoard().piece(position1) == null && getBoard().piece(position2) == null && getBoard().piece(position3) == null ){
                    // Now I must include this possibility to the matrix f possible movements of the King

                    matrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }

        }

        return matrix;
    }
}
