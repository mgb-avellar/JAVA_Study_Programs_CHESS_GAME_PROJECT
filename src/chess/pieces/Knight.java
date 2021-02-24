package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    // Esta é a classe que representa a peça Cavalo


    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
        /*
         Faço isso para que seja impresso um N no tabuleiro na posição em que
         o cavalo está
         */
    }
}
