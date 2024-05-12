import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

enum GameState {
    GAMER,
    WIN,
    LOSE,
}

public class Hangman {
    private String word;
    private ArrayList<Character> guesses = new ArrayList<>();

    private String[] hangmanStages = {
            "  +-----+\n  |     |\n        |\n        |\n        |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n        |\n        |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n  |     |\n        |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n /|     |\n        |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n /|\\    |\n        |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n /|\\    |\n  |     |\n        |\n========",
            "  +-----+\n  |     |\n  O     |\n /|\\    |\n  |     |\n /      |\n========",
            "  +-----+\n  |     |\n  O     |\n /|\\    |\n  |     |\n / \\    |\n========",
            // " +-----+\n | |\n [O] |\n /|\\ |\n | |\n / \\ |\n========"
    };

    private void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private Character getChar() throws IOException {
        Character ch = Character.valueOf('\0');

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

        while (ch.compareTo('\0') == 0) {
            printGuesses();
            printBoard();

            System.out.println("Enter a character: ");

            String input = stdinReader.readLine().trim();

            if (input.length() == 1) {
                if (guesses.contains(input.charAt(0))) {
                    System.out.println("This character was already guessed! Try another one.");
                    continue;
                }

                ch = input.charAt(0);
                break;
            }

            System.out.println("Invalid input. Please enter only one character.\n");
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
        System.out.println();
    }

    private Integer getHangmanIndex() {

        Integer index = 0;

        for (Character guess : guesses) {
            if (word.indexOf(guess) == -1) {
                index++;
            }
        }

        if (index >= hangmanStages.length) {
            index = hangmanStages.length - 1;
        }

        return index;
    }

    private void printTheMan() {
        System.out.println(hangmanStages[getHangmanIndex()]);
    }

    private String getRandomWordFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }

    private GameState getGameState() {
        if (getHangmanIndex() >= hangmanStages.length - 1) {
            return GameState.LOSE;
        }

        for (Character ch : word.toCharArray()) {
            if (guesses.indexOf(ch) == -1) {
                return GameState.GAMER;
            }
        }

        return GameState.WIN;
    }

    public void init() throws IOException {
        this.word = getRandomWordFromFile("words.txt");

        while (true) {
            GameState gameState = getGameState();

            if (gameState == GameState.WIN) {
                clearTerminal();
                printTheMan();

                System.out.println("\nYou won, congrats man");
                System.out.println("The word was: " + this.word);
                return;
            }

            if (gameState == GameState.LOSE) {
                clearTerminal();
                printTheMan();

                System.out.println("\nYou lost, lol");
                System.out.println("The word was: " + this.word);
                return;
            }

            clearTerminal();
            printTheMan();

            guesses.add(getChar());
        }
    }
}
