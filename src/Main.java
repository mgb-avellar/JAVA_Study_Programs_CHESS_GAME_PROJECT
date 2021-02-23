import chess.ChessMatch;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("Instantiating a chessMatch (testing:)");
        System.out.println();

        ChessMatch chessMatch = new ChessMatch();

        UI.printBoard(chessMatch.getPieces());


    }
}
