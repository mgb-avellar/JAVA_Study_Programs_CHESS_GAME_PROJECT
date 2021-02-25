package chess.pieces;

import boardgame.Board;
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

    @Override
    public boolean[][] possibleMoves() {
        /*
         For now, we are not going to implement the possible moves of the King.
         We are just going to create a boolean matrix of the size of the board.
         */
        boolean[][] matrix = new boolean[getBoard().getNumberRows()][getBoard().getNumberColumns()];
        return matrix;
    }
}
