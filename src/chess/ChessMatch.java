package chess;

import boardgame.Board;

public class ChessMatch {

    /*
    This class is the core of our systems and it is the class that contains the rules
    for the chess game.
     */

    private Board board; // Every match has it own board

    public ChessMatch() {
        this.board = new Board(8,8);
    }

    public ChessPiece[][] getPieces() {

        /*
           This method returns a chess piece matrix correspondent to this
           match.
           Notice that I want leave the layers separated. I want the chess
           layer to see only the chess pieces (ChessPiece class in chess package)
           and not the pieces of the board layer (Piece class in boardgame package),
           even though the ChessPieces extends Piece.
         */

        ChessPiece[][] auxMatrix = new ChessPiece[board.getNumberRows()][board.getNumberColumns()];

        /*
        I run through all the board matrix pieces and for each piece I must downcast the piece
        to ChessPiece.
         */

        for (int i = 0; i < board.getNumberRows(); i++) {

            for (int j = 0; j < board.getNumberColumns(); j++) {

                auxMatrix[i][j] = (ChessPiece) board.piece(i, j);
            }

        }
        return auxMatrix;

    }

}
