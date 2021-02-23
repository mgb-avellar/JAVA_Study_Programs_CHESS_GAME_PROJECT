package boardgame;

public class Board {

    /*
    This class must have the number of columns and lines and a matrix of pieces.
     */

    private int numberRows;
    private int numberColumns;
    private Piece[][] piecesMatrix;

    public Board(int numberRows, int numberColumns) {
        this.numberRows = numberRows;
        this.numberColumns = numberColumns;

        // The piecesMatrix will be initialized manually.

        this.piecesMatrix = new Piece[this.numberRows][this.numberColumns];
    }

    public Board() {
    }

    public int getNumberRows() {
        return numberRows;
    }

    public void setNumberRows(int numberRows) {
        this.numberRows = numberRows;
    }

    public int getNumberColumns() {
        return numberColumns;
    }

    public void setNumberColumns(int numberColumns) {
        this.numberColumns = numberColumns;
    }
}
