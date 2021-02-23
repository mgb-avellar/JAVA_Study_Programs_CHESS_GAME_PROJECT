package boardgame;

public class Piece {

    // The piece knows its matricial position
    protected Position position;

    // The piece also knows the board where it is
    private Board board;

    public Piece(Board board) {
        this.board = board;

        // A freshly created piece begins wit null position
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
}
