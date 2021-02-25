package chess;

import boardgame.Position;

public class ChessPosition {

    private char columnChessChar;
    private int rowChessNumber;

    public ChessPosition(char columnChessChar, int rowChessNumber) {

        // Defensive programming
        if ( columnChessChar < 'a' || columnChessChar > 'h' || rowChessNumber < 1 || rowChessNumber > 8) {

            throw new ChessException("Error in instantiating ChessPosition: " +
                    "valid values for columns are from 'a' to 'h' and for rows from 1 to 8.");
        }

        this.columnChessChar = columnChessChar;
        this.rowChessNumber = rowChessNumber;
    }



    public ChessPosition() {

    }

    public char getColumnChessChar() {
        return columnChessChar;
    }

    public int getRowChessNumber() {
        return rowChessNumber;
    }

    // Defensive programming: no setters

    /*
     Two methods will be created now: converting chess position to board (matricial) position
     and vice versa.
     Recall the conversions:
     rows:
     matrix_row = 8 - chess_row

     columns:
     a = 0
     b = 1
     c = 2
     ...

     => 'a' - 'a' = 0
        'b' - 'a' = 1
        'c' - 'a' = 2
        ...
     */

    protected Position toPosition () {

        return (new Position(8 - rowChessNumber, columnChessChar - 'a'));
    }

    protected static ChessPosition fromPosition(Position position) {

        return ( new ChessPosition((char)('a' + position.getColumn()), 8 - position.getRow()) );
    }

    @Override
    public String toString() {

        return "" + columnChessChar + rowChessNumber;
    }

}
