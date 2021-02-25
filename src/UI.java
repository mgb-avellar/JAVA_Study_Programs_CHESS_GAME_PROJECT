import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // Improving chess board printing with colors

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Below, a method for reading a chess position from the user

    public static ChessPosition readChessPosition(Scanner sc) {

        // Recall that the user will type something like a1, f6, h2 etc;

        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));

            return new ChessPosition(column, row);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Error in reading ChessPosition: " +
                    "valid values for columns are from 'a' to 'h' and for rows from 1 to 8.");
        }

        // The try-catch block prevents mismatch input format
    }

    /*
     Now we are going to build a method to print the match
     */

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {

        /*
        Printing the match is more than print the board. We must print the board with the current pieces
        and the turns. I need to update the Main class to print the match instead the board
         */

        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);  // printing captured pieces
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());

        // The test below is to test if there is some player in check.

        if (chessMatch.getCheck()) { // If this statement is true, there is someone in check.

            System.out.println("CHECK! ");
        }

    }

    public static void printBoard(ChessPiece[][] pieces) {

        System.out.println("      _________________");
        for (int i = 0; i < pieces.length; i++) {

            System.out.print("   " + (8-i) + " | ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false); // The 'false' is a modification due to the background coloring
            }
            System.out.println("|");

        }
        //System.out.println("      _________________" + "\u0305");
        System.out.println("       " + "\u0305" + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305");
        System.out.println("       a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {

        // Overload of print board to color the background of possible moves
        // I need to change also the printPiece method
        System.out.println("      _________________");
        for (int i = 0; i < pieces.length; i++) {

            System.out.print("   " + (8-i) + " | ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]); // The 'possibleMoves' is a modification due to the background coloring
            }
            System.out.println("|");

        }
        //System.out.println("      _________________" + "\u0305");
        System.out.println("       " + "\u0305" + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305"
                + " " + "\u0305"+ " " + "\u0305"+ " " + "\u0305");
        System.out.println("       a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {

        // Modification in this method due to coloring the background of possible moves

        if (background) {

            System.out.print(ANSI_GREEN_BACKGROUND);
        }

        // Printing a single piece

        if (piece == null) {

            System.out.print("-" + ANSI_RESET);
        }
        else {

            if (piece.getColor() == Color.WHITE) {

                System.out.print(ANSI_RED + piece + ANSI_RESET);
            }
            else {

                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }

        }

        System.out.print(" ");
    }

    /*
    We implement a method to print a list of captured pieces. (We also need to update Main.)
     */

    private static void printCapturedPieces(List<ChessPiece> captured) {

        // Filterisng pieces by colors and inserting them in a list

        List<ChessPiece>  white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece>  black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());

        System.out.println("Captured pieces: ");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.print(Arrays.toString(white.toArray()));
        System.out.println(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.print(Arrays.toString(black.toArray()));
        System.out.println(ANSI_RESET);

    }
}
