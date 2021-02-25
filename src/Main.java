import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("Instantiating a chessMatch (testing:)");
        System.out.println();

        ChessMatch chessMatch = new ChessMatch();

        while(true) {
            try {

                // Dealing with ChessException and InputMismatchException exceptions

                UI.clearScreen();
                // UI.printBoard(chessMatch.getPieces());
                UI.printMatch(chessMatch);
                System.out.println();
                System.out.print("Source position: ");
                ChessPosition source = UI.readChessPosition(sc);

                // Modification due to coloring the background of the board for possible moves

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves); // Class overload


                System.out.println();
                System.out.print("Target position: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            }
            catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }


    }
}
