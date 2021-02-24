package boardgame;

public class Position {

    /*
    A posição do tabuleiro é uma posição matricial, vai de (0,0) a (8,8), o que é
    diferente da posição do xadrez, cujas linhas vão de 1 a 8 e as colunas de 'a' a 'h'.
    Inclusive, a posição matricial é lida (row, column) e a do xadrez (column, row)
     */

    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position() {

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString () {
        return row + ", " + column;
    }
}
