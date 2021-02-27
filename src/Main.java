import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println();

        // Together with the match, we create a list to store the captured pieces

        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedList = new ArrayList<>();

        while(!chessMatch.getCheckMate()) { // Now, the match continues while the check-mate condition is not reached
            try {

                // Dealing with ChessException and InputMismatchException exceptions

                UI.clearScreen();
                // Updating to print the match
                UI.printMatch(chessMatch, capturedList); // Insertion of 'captured' is to print the list of captured pieces
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

                // Testing if a piece was captured to insert it in the list
                if (capturedPiece != null) {
                    capturedList.add(capturedPiece);
                }

                // Asking for the user enter the piece to be promoted if it is the case
                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/N/R/Q): ");
                    String type = sc.nextLine();
                    chessMatch.replacePromotedPiece(type);
                }
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

        UI.clearScreen();
        UI.printMatch(chessMatch, capturedList);

    }
}
