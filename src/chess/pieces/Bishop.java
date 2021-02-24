package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
    // Esta é a classe que representa a peça Bispo


    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
        /*
         Faço isso para que seja impresso um B no tabuleiro na posição em que
         o bispo está
         */
    }
}
