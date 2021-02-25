package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    /*
    This class is the core of our systems and it is the class that contains the rules
    for the chess game.
     */

    private int turn;
    private Color currentPlayer;

    private Board board; // Every match has it own board

    public ChessMatch() {
        this.board = new Board(8,8);
        this.turn = 1; // I begin in turn 1
        this.currentPlayer = Color.WHITE; // The white player is the one who begins to play
        initialSetup(); // See below for explanations
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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
    I want to color the background of the board with the possible moves of a piece.
    I need to change the Main class and the UI class too.
     */

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {

        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);

        return board.piece(position).possibleMoves();
    }



    /*
    Method performChessMove - receives origin/source position, destiny/target position and returns
    a captured position if this is the case (this last action will be coded later)
     */

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);  // Created after implementing the Rook moves
        Piece capturedPiece = makeMove(source, target);
        nextTurn(); // Must be called after a move
        return (ChessPiece) capturedPiece;
    }

    // We need the methods below to complete the code above

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on the source position.");
        }

        /*
         Problem is that when changing turns I must check if the actual player is of the same color
         of the chosen piece. In other words, I can not move any of my adversary's piece. So:
         */

        if ( currentPlayer != ((ChessPiece) board.piece(position)).getColor() ) {
            throw new ChessException("The chosen piece is not yours.");
        }

        /*
        After the creation of the methods of possible moves, we need to update our
        validateSourcePosition method to check whether there is possible moves: if
        there is not, then we must throw an exception
         */

        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece.");
        }

    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can not move to the target position");
        }
    }

    // the method to change turns
    private void nextTurn () {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Piece makeMove(Position source, Position target) {

        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
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

        placeNewPiece('c', 4, new King(board, Color.BLACK));


        // After this, I must call this method in the constructor above.
        // Notice that Position(2,1) is a matrix position of the board layer, not of the chess layer
    }


}
