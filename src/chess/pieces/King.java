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
}
