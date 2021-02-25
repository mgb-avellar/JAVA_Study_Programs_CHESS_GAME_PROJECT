package boardgame;

public abstract class Piece {

    // The piece knows its matricial position
    protected Position position;

    // The piece also knows the board where it is
    private Board board;

    public Piece(Board board) {
        this.board = board;

        // A freshly created piece begins with null position
        this.position = null;
    }

    public Piece() {

    }

    protected Board getBoard() {
        return board;
        /*
        This getter is slightly different from what we are used to in this course.
        Here, we are preventing this getter from being accessed by the Chess layer.
        This getter is for internal use of the Board layer only.
        This action is generally a good practice for systems with many layers.
         */
    }

    /*
    We are going to implement the possibleMoves method, which is going to be abstract.
    It is abstract because I do not know the possible moves of a generic piece: in this
    project I only know the exact possible movements of a chess piece.
    Besides, we are also going to implement the following methods:
    - possibleMove, which tells us is a given piece can move to a specific position and
    - isThereAnyPossibleMove, which tells us if the piece is stuck or not
     */

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];

        // This is an example of hook methods
        // The operation will be possible only when a concrete class implements
        //   the abstract method possibleMoves();
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] auxMatrix = possibleMoves();
        for (int i = 0; i < auxMatrix.length; i++) {

            for (int j = 0; j < auxMatrix.length; j++) {

                if (auxMatrix[i][j]) {

                    return true;
                }
            }
        }
        return false;
    }



}
