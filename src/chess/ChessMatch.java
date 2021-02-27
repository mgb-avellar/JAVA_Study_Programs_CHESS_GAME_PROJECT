package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    /*
    This class is the core of our systems and it is the class that contains the rules
    for the chess game.
     */

    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable; // This variable tells me if a Pawn is vulnerable to the En Passant move
    private ChessPiece promoted; // For a Pawn who reaches the vertical border of the opponents

    private Board board; // Every match has it own board

    /*
    We begin to implement the control over the pieces on the board and the captured pieces, storing them in lists.
    We do this to take a piece off the board when it is captured.
    We will have to update the constructor, the PlaceNewPiece and MakeMove
     */

    private List<Piece> piecesOnTheBoardList = new ArrayList<>();
    private List<Piece> capturedPiecesList = new ArrayList<>();

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

    public boolean getCheck() {
        return check;
        // This getter is needed to fully implement the check logic. I must leave this attribute accessible in the UI class to release a warning.
    }

    public boolean getCheckMate() {
        return checkMate;
        // This getter is needed to fully implement the check-mate logic. I must leave this attribute accessible in the UI class to release a warning.
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
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

        /*
        In implementing the check logic, I need to guarantee that I cannot put myself in a check position.
        Thus, I must test this each time I make a movement.
        I also need to check if my opponent is in check.
        (See the two methods below.)
         */

        if ( testCheck(currentPlayer)) { // If this statement is true, then I put myself in check

            undoMove(source, target, capturedPiece);
            throw new ChessException("You can not put yourself in check.");
        }

        // After the move, I must to know if the moved piece is a Pawn and if that Pawn moved 2 squares
        // For that, I create a reference for this moved piece, as below. It will be used to test the
        //  vulnerability to a En Passant move if the piece was, in fact, a Pawn.

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        // The promotion move must be tested before the check test since when the promotion occurs,
        //   there is a possibility that the promoted piece leaves the opponent's King in check automatically

        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ( (movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7) ) {
                promoted = (ChessPiece) board.piece(target);
                promoted = replacePromotedPiece("Q"); // I put Q by default, then I ask the user to choose the piece

            }
        }

        check = ( testCheck(opponent(currentPlayer)) ) ? true : false;

        if (testCheckMate(opponent(currentPlayer))) {

            checkMate = true;
        }
        else {
            nextTurn(); // Must be called after a move
            // I moved this statement inside the 'else' to test the check-mate condition: if it is true, the match must end.
            //  If not, the match must continue.
        }

        // For implementing the En Passant movement
        /*
        This special move only occurs if the Pawn moves two squares in its first move. Then it becomes vulnerable
        to suffer this special capture from an opponent Pawn.
         */

        // If the piece is a pawn and has moved 2 lines then
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        }
        else {
            enPassantVulnerable = null;
        }


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

    // The method to promote a Pawn that reaches the vertical border of the opponent
    public ChessPiece replacePromotedPiece(String type) {
        // Defensive programming
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted.");
        }

        if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
            throw new InvalidParameterException("Invalid type for promotion.");
        }

        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos); // I remove the promoted Pawn
        piecesOnTheBoardList.remove(p); // I remove the promoted Pawn from the list of pieces

        // I must instantiate the new piece, of the type chosen by the user. For that I need
        //   an auxiliary method (see below) called newPiece

        ChessPiece newPiece = newPiece(type, promoted.getColor());

        board.placePiece(newPiece, pos);

        piecesOnTheBoardList.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece (String type, Color color) {
        if (type.equals("B")) {
            return new Bishop(board, color);
        }
        else if (type.equals("N")) {
            return new Knight(board, color);
        }
        else if (type.equals("R")) {
            return new Rook(board, color);
        }
        else {
            return new Queen(board, color);
        }
    }

    private Piece makeMove(Position source, Position target) {

        /*
        To implement the move counter, we must modify the statement of Piece auxP to ChessPiece auxP,
        downcasting the board.remove(). This will not influence the rest of the code, since
        in board.placePiece(auxP, target); a upcasting is automatic.
         */
        //Piece auxP = board.removePiece(source);
        ChessPiece auxP = (ChessPiece) board.removePiece(source);
        auxP.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(auxP, target);

        /*
         If I capture a piece when moving, I must remove it from the list of pieces on the board and
         insert it in the list of captured pieces.
         */

        if (capturedPiece != null) {

            piecesOnTheBoardList.remove(capturedPiece);
            capturedPiecesList.add(capturedPiece);
        }

        /*
        Now it is time to implement the logic of the special move castling
         */

        // First, the king-side castling

        if (auxP instanceof King && target.getColumn() == source.getColumn() + 2) {
            // I move the Rook based on the King's position which is given by source.
            Position sourceRook1 = new Position(source.getRow(), source.getColumn()+3);
            Position targetRook1 = new Position(source.getRow(), source.getColumn()+1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook1); // I remove the Rook from its current position
            board.placePiece(rook,targetRook1); // I place the Rook at the target position
            rook.increaseMoveCount();
        }

        // Second, the queen-side castling

        if (auxP instanceof King && target.getColumn() == source.getColumn() - 2) {
            // I move the Rook based on the King's position which is given by source.
            Position sourceRook1 = new Position(source.getRow(), source.getColumn()-4);
            Position targetRook1 = new Position(source.getRow(), source.getColumn()-1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceRook1); // I remove the Rook from its current position
            board.placePiece(rook,targetRook1); // I place the Rook at the target position
            rook.increaseMoveCount();
        }

        // For implementing the En Passant move
        // Every time I move a piece, I must test if it was a En Passant movement;
        // In the case of an En Passant move, I need a special treatment since the mechanics is different

        if (auxP instanceof Pawn) {
            // Have my Pawn moved in diagonal and did not capture a opponent's piece? Is so, it is a En Passant
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (auxP.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                    // This command tells me to capture the black Pawn that is just below the white Pawn
                }
                else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                    // This command tells me to capture the white Pawn that is just above the black Pawn
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPiecesList.add(capturedPiece);
                piecesOnTheBoardList.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    /*
        I need to create a method to undo the move if I put my King deliberately in check.
     */

    private void undoMove(Position source, Position target, Piece capturedPiece) {

        // I must do the same as above for implemented the move counter.
        ChessPiece auxP = (ChessPiece) board.removePiece(target);
        auxP.decreaseMoveCount();
        board.placePiece(auxP, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPiecesList.remove(capturedPiece);
            piecesOnTheBoardList.add(capturedPiece);
        }

        /*
        Now it is time to implement the logic of the special move castling in the undoMove method
        I must undo what was done in the makeMove method
         */

        // First, the king-side castling

        if (auxP instanceof King && target.getColumn() == source.getColumn() + 2) {
            // I move the Rook based on the King's position which is given by source.
            Position sourceRook1 = new Position(source.getRow(), source.getColumn()+3);
            Position targetRook1 = new Position(source.getRow(), source.getColumn()+1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook1); // I remove the Rook from its current position
            board.placePiece(rook,sourceRook1); // I place the Rook at the target position
            rook.decreaseMoveCount();
        }

        // Second, the queen-side castling

        if (auxP instanceof King && target.getColumn() == source.getColumn() - 2) {
            // I move the Rook based on the King's position which is given by source.
            Position sourceRook1 = new Position(source.getRow(), source.getColumn()-4);
            Position targetRook1 = new Position(source.getRow(), source.getColumn()-1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetRook1); // I remove the Rook from its current position
            board.placePiece(rook,sourceRook1); // I place the Rook at the target position
            rook.decreaseMoveCount();
        }

        // For implementing the En Passant move: undoing it

        if (auxP instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {

                // To undo the En Passant, we must recall that the undoMove puts the captured piece back in
                //   the target position of the piece who captured it. This is wrong for the En Passant move:
                //   we must put the captured piece back in the row 3, if white, or 4, if black.
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (auxP.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                }
                else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }


    }

    /*
        We create a method that returns black if the color of the piece is white and white if
        the color of the piece is black.
        This method is needed to implement the check logic
     */

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /*
    I create the method 'king(color)'. We need it to localize the king of a given color because
    we must always keep attention to the board since we can check the opponent's King when we
    are deciding where to move a piece to.
     */

    private ChessPiece king(Color color) {

        List<Piece> list =  piecesOnTheBoardList.stream().filter(x -> ((ChessPiece) x).getColor() == color ).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board."); // This exception should never occur.
    }

    /*
    Now, I will implement a method to test a check. For this, I need to run over all of my adversary's pieces and
    see if for anyone of them there is a possible movement that can check my King.
     */

    private  boolean testCheck(Color color) {

        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPiecesList = piecesOnTheBoardList.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color) ).collect(Collectors.toList());
        // In the above line I use the opponent(Color color) method.

        for (Piece p : opponentPiecesList) {

            boolean[][] matrix = p.possibleMoves();
            if ( matrix[kingPosition.getRow()][kingPosition.getColumn()]  ) {  // If this statement is true, my King in on check

                return true;
            }
            /*
            This 'for' will look for each adversary's pieces possible moves if the position of my King belongs to
            the track of one of these possible movements.
             */
        }
        return false;
    }

    private  boolean testCheckMate(Color color) {

        if(!testCheck(color)) { // If this color is not in check, it is not in check-mate.
            return false;
        }

        List<Piece> list = piecesOnTheBoardList.stream().filter(x -> ((ChessPiece) x).getColor() == color ).collect(Collectors.toList());

        for (Piece p : list) {

            boolean[][] matrix = p.possibleMoves();

            for (int i = 0; i < board.getNumberRows(); i++) {
                for (int j = 0; j < board.getNumberColumns(); j++) {

                    if (matrix[i][j]) { // This statement is equivalent to "is the position (i,j) a possible movement?
                        /*
                        The logic here is the following:
                        For each piece p that is of my color, I move it to a possible position;
                        then I test again if this movement free my King from the check;
                        if so, then I return 'false', i.e., I am not in check anymore.

                         */
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source,target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target,capturedPiece); // I must undo the movement since I moved the piece only to test the check
                        if (!testCheck) {
                            return  false;
                        }
                    }
                }
            }
        }
        return true;

    }

    /*
    I create a method for placing a new piece on the chess board using the chess labels
    for positions (section 155)

     */

    private void placeNewPiece(char column, int row, ChessPiece piece) {

        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        // Each time I instantiate a new piece on the board, I must insert them in the list of pieces on the board
        piecesOnTheBoardList.add(piece);

    }

    /* Below, I create the method initialSetup, responsible for initializing the chess match
    putting the pieces on the board.
    */

    private void initialSetup() {

        // White pieces

        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        /*
         The 'this' word in the King's instantiation is because of the King piece reference to chessMatch;
         by chessMatch we mean the current state of the board
         */
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));


        // Black pieces

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));




        // After this, I must call this method in the constructor above.
        // Notice that Position(2,1) is a matrix position of the board layer, not of the chess layer
    }


}
