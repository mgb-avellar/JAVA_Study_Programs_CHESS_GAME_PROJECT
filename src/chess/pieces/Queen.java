package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    // Esta é a classe que representa a peça Rainha


    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "Q";
        /*
         Faço isso para que seja impresso um Q no tabuleiro na posição em que
         a rainha está
         */
    }

    @Override
    public boolean[][] possibleMoves() {

        /*
        The Queen can move in every direction we want: above, below, left, right, northwest,
        northeast, southwest and southeast, how many squares we want.
        The Queen incorporates the possible movements of the Rook and the Bishop.
         */
        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];

        Position p = new Position(0, 0);

        // above
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // left
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // right
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // below
        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // nw
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // ne
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // se
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // sw
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        return matrix;
    }
}
