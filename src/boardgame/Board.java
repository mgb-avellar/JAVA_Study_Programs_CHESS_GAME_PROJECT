package boardgame;

public class Board {

    /*
    This class must have the number of columns and lines and a matrix of pieces.
     */

    private int numberRows;
    private int numberColumns;
    private Piece[][] piecesMatrix;

    public Board(int numberRows, int numberColumns) {

        // Defensive programming:

        /*
        if (numberRows < 1 || numberColumns < 1) {

            throw new BoardException("Error in creating board: there must be" +
                    " at least 1 row and 1 column.");
        }
        */

        if (numberRows != 8 && numberColumns != 8) {

            throw new BoardException("Error in creating board: a chess board" +
                    " has 8 rows and 8 columns.");
        }

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

    public int getNumberColumns() {
        return numberColumns;
    }

    // Defensive programming: no setters for number of rows and number of columns.

    public Piece piece(int row, int column) {
        // Defensive programming
        if (!positionExists(row, column)) {

            throw new BoardException("Position not on the board.");
        }

        return piecesMatrix[row][column];
    }

    public Piece piece(Position position) {
        // Defensive programming
        if (!positionExists(position)) {

            throw new BoardException("Position not on the board.");
        }
        return piecesMatrix[position.getRow()][position.getColumn()];
    }

    /*
     A seguir, criaremos um método para colocar uma determinada peça em
     uma posição específica, ambos recebidos pelo método
     */

    public void placePiece(Piece piece, Position position) {

        // Defensive programming
        if (thereIsAPiece(position)) {

            throw new BoardException("There is already a piece on position " + position);
        }


        piecesMatrix[position.getRow()][position.getColumn()] = piece;
        piece.position = position;  // Preciso informar que a posição da peça não é mais nula
        /*
        Note que a posição da peça, que está na classe Piece, no pacote
        boardgame, é acessível porque lá definimos essa variável como
        protected e Board e Piece estão no mesmo pacote.
         */
    }

    /*
    The next two methods are important:
    - positionExists tells me if the position exists in the board, so a piece can be or move there
    - thereIsAPiece tells me if there is a piece in that particular position where I want to move
    another piece to.
     */
    public boolean positionExists (int row, int column) {
        // Sometimes, it can be easier to check if the position exists via row and column
        // Besides, this method will help us when we check the position existence when
        //   provided the position (see below)

        return (row >= 0 && row < numberRows && column >= 0 && column < numberColumns);
    }

    public boolean positionExists (Position position) {

        return (positionExists(position.getRow(), position.getColumn()));
    }

    public boolean thereIsAPiece (Position position) {

        // Defensive programming
        if (!positionExists(position)) {

            throw new BoardException("Position not on the board.");
        }

        return piece(position) != null;
    }
}
