package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    // Esta é a classe que representa a peça Peão


    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
        /*
         Faço isso para que seja impresso um P no tabuleiro na posição em que
         o peão está
         */
    }

    @Override
    public boolean[][] possibleMoves() {

        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];

        Position p = new Position(0, 0);

        if (getColor() == Color.WHITE) {

            // Below, the general movements of the pawn
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }

            /*
            In the next block of lines below, I test if the pawn is in its first move. If so, it has the option
            of moving 2 squares ahead, only this time, if there is no piece in the square immediately ahead.
             */
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                matrix[p.getRow()][p.getColumn()] = true;
            }

            /*
            Diagonal movements:
             */
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }
        else {

            // The same logic applies to the black pawn

            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }

        return matrix;
    }
}
