package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    // Esta é a classe que representa a peça Rei


    public King(Board board, Color color) {
        super(board, color);
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

    @Override
    public boolean[][] possibleMoves() {
        /*
         For now, we are not going to implement the possible moves of the King.
         We are just going to create a boolean matrix of the size of the board.
         */
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

        return matrix;
    }
}
