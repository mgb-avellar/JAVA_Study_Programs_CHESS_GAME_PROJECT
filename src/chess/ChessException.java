package chess;

import boardgame.BoardException;

public class ChessException extends BoardException {

    private static final long serialVersionUID = 1L;

    public ChessException(String msg) {

        super(msg);
    }

    /*
     This class was updated from extends RunTimeException to BoardException
     after the implementation of the method validateSourcePosition
     */

}
