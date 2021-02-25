package chess.pieces;

import boardgame.Board;
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
        /*
         For now, we are not going to implement the possible moves of the Pawn.
         We are just going to create a boolean matrix of the size of the board.
         */
        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];
        return matrix;
    }
}
