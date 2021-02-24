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

    public Piece piece(int row, int column) {
        return piecesMatrix[row][column];
    }

    public Piece piece(Position position) {
        return piecesMatrix[position.getRow()][position.getColumn()];
    }

    /*
     A seguir, criaremos um método para colocar uma determinada peça em
     uma posição específica, ambos recebidos pelo método
     */

    public void placePiece(Piece piece, Position position) {

        piecesMatrix[position.getRow()][position.getColumn()] = piece;
        piece.position = position;  // Preciso informar que a posição da peça não é mais nula
        /*
        Note que a posição da peça, que está na classe Piece, no pacote
        boardgame, é acessível porque lá definimos essa variável como
        protected e Board e Piece estão no mesmo pacote.
         */
    }
}
