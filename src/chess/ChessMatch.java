package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    /*
    This class is the core of our systems and it is the class that contains the rules
    for the chess game.
     */

    private Board board; // Every match has it own board

    public ChessMatch() {
        this.board = new Board(8,8);
        initialSetup(); // See below for explanations
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

    /*
    I create a method for placing a new piece on the chess board using the chess labels
    for positions (section 155)

     */

    private void placeNewPiece(char column, int row, ChessPiece piece) {

        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    /* Below, I create the method initialSetup, responsible for initializing the chess match
    putting the pieces on the board.
    */

    private void initialSetup() {
        /*
        board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
        board.placePiece(new King(board, Color.BLACK), new Position(0,4));
        board.placePiece(new King(board, Color.WHITE), new Position(7,4));
        board.placePiece(new Queen(board, Color.WHITE), new Position(3,7));
        board.placePiece(new Bishop(board, Color.WHITE), new Position(2,6));
        board.placePiece(new Pawn(board, Color.WHITE), new Position(1,5));
        board.placePiece(new Knight(board, Color.WHITE), new Position(5,7));

        // The code above was written for testing, but the positions are entered
        //   in matricial coordinates. initialSetup was one of the first classes to be created.
        // The class written above initialSetup (placeNewPiece) is newer than initialSetup.
        // Thus, after the creation of placeNewPiece, the code in initialSetup must be changed
        //   to accept chess-board coordinates.

         */

        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));


        // After this, I must call this method in the constructor above.
        // Notice that Position(2,1) is a matrix position of the board layer, not of the chess layer
    }


}
