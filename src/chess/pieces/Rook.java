package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

    // Esta é a classe que representa a peça Torre


    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
        /*
         Faço isso para que seja impresso um R no tabuleiro na posição em que
         a torre está
         */
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];

        // Recall that we are working here with matrix coordinates

        Position auxP = new Position(0, 0);

        // above
            // I initialize an auxiliary variable to the just above position of my Rook
        auxP.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(auxP) && !getBoard().thereIsAPiece(auxP)) {
            /*
             While there is no impediment to my moves, e.g., another piece, I continue
             to mark the positions as true
             */
            matrix[auxP.getRow()][auxP.getColumn()] = true;
            auxP.setRow(auxP.getRow() - 1);
        }
        if (getBoard().positionExists(auxP) && isThereOpponentPiece(auxP)) {
            /*
            Now, if I find another piece on my way, I must test is it is friendly or not:
            if not, I can move one position forward
             */
            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // Repeating the same logic for the other directions

        // left
        auxP.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(auxP) && !getBoard().thereIsAPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
            auxP.setColumn(auxP.getColumn() - 1);
        }
        if (getBoard().positionExists(auxP) && isThereOpponentPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // right
        auxP.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(auxP) && !getBoard().thereIsAPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
            auxP.setColumn(auxP.getColumn() + 1);
        }
        if (getBoard().positionExists(auxP) && isThereOpponentPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        // below
        auxP.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(auxP) && !getBoard().thereIsAPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
            auxP.setRow(auxP.getRow() + 1);
        }
        if (getBoard().positionExists(auxP) && isThereOpponentPiece(auxP)) {
            matrix[auxP.getRow()][auxP.getColumn()] = true;
        }

        return matrix;


    }
}
