package chess.pieces;

import boardgame.Board;
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
}
