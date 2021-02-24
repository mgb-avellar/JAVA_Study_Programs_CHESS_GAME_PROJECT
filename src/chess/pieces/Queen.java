package chess.pieces;

import boardgame.Board;
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
}
