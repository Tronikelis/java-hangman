import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

enum GameState {
    GAMER,
    WIN,
    // this doesnt do anything
    LOSE,
}

class Hangman {
    private String word = "copium";
    private ArrayList<Character> guesses = new ArrayList<>();

    private void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private Character getChar() throws IOException {
        Character ch = Character.valueOf('\0');

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

        while (ch.compareTo('\0') == 0) {
            System.out.println("Enter a character: ");

            String input = stdinReader.readLine().trim();

            if (input.length() == 1) {
                if (guesses.contains(input.charAt(0))) {
                    System.out.println("This character was already guessed! Try another one.");
                    printGuesses();
                    printBoard();
//                    ch = '\0';
                    continue;
                }

                ch = input.charAt(0);
                break;
            }

            System.out.println("Invalid input. Please enter only one character.");
            printGuesses();
            printBoard();
        }

        return ch;
    }

    private void printGuesses() {
        if (guesses.size() == 0) {
            return;
        }

        System.out.print("\nYour guesses: ");
        for (Character guess : guesses) {
            System.out.printf("%c ", guess);
        }

        System.out.println();
    }

    private void printBoard() {
        System.out.print("The word: ");
        for (Character ch : word.toCharArray()) {
            // is ch inside guesses?
            if (guesses.indexOf(ch) != -1) {
                System.out.printf("%c", ch);
            } else {
                System.out.printf("_", ch);
            }

            System.out.printf(" ", ch);
        }
        System.out.println("");
    }

    private GameState didEnd() {
        for (Character ch : word.toCharArray()) {
            if (guesses.indexOf(ch) == -1) {
                return GameState.GAMER;
            }
        }

        return GameState.WIN;
    }

    public void init() throws IOException {
        while (true) {
            if (didEnd() == GameState.WIN) {
                System.out.println("you won, congrats man");
                return;
            }

            clearTerminal();

            printGuesses();
            printBoard();

            guesses.add(getChar());
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Hangman hangman = new Hangman();
        hangman.init();
    }
}
