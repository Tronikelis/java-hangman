import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        Character ch = '\0';

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

        while (ch.compareTo('\0') == 0) {
            printGuesses();
            printBoard();

            System.out.println("Enter a character: ");

            String input = stdinReader.readLine().trim().toLowerCase();

            if (input.length() == 1) {
                if (guesses.contains(input.charAt(0))) {
                    System.out.println("This character was already guessed! Try another one.");
                    continue;
                }

                if (!Character.isLetter(input.charAt(0))) {
                    System.out.println("Make sure that the input is an alphabetical character.");
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
        if (guesses.isEmpty()) {
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
            if (guesses.contains(ch)) {
                System.out.printf("%c", ch);
            } else {
                System.out.print("_");
            }

            System.out.print(" ");
        }
        System.out.println();
    }

    private Integer getHangmanIndex() {

        int index = 0;

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
        lines.removeIf(l -> l.isEmpty());

        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }

    private GameState getGameState() {
        if (getHangmanIndex() >= hangmanStages.length - 1) {
            return GameState.LOSE;
        }

        for (Character ch : word.toCharArray()) {
            if (!guesses.contains(ch)) {
                return GameState.GAMER;
            }
        }

        return GameState.WIN;
    }

    private Integer timeNowSeconds() {
        long now = System.currentTimeMillis() / 1000L;
        return (int) now;
    }

    public void init(String filePath) throws IOException {
        this.word = getRandomWordFromFile(filePath);

        Integer past = timeNowSeconds();
        PersonalBest pb = new PersonalBest();

        while (true) {
            GameState gameState = getGameState();
            int timeTaken = timeNowSeconds() - past;

            if (gameState == GameState.WIN) {
                clearTerminal();
                printTheMan();

                System.out.println("\nYou won, congrats man");
                System.out.println("The word was: " + this.word);

                if (timeTaken < pb.get() || pb.get() == 0) {
                    System.out.println("NEW PB! " + timeTaken + "sec");
                    pb.set(timeNowSeconds() - past);
                }

                System.out.println("took " + timeTaken + "sec");

                return;
            }

            if (gameState == GameState.LOSE) {
                clearTerminal();
                printTheMan();

                System.out.println("\nYou lost, lol");
                System.out.println("The word was: " + this.word);

                System.out.println("took " + timeTaken + "sec");

                return;
            }

            clearTerminal();
            printTheMan();

            guesses.add(getChar());
        }
    }
    public void guessLongWord(String longWord) throws IOException {
        this.word = longWord.toLowerCase();

        Integer startTime = timeNowSeconds();
        PersonalBest pb = new PersonalBest();

        while (true) {
            int currentTime = timeNowSeconds();
            int elapsedTime = currentTime - startTime;
            int remainingTime = 45 - elapsedTime;

            if (remainingTime <= 0) {
                clearTerminal();
                System.out.println("Time is up bucko, you failed XD\n");
                System.out.printf("Also the word was: " + this.word);
                return;
            }

            GameState gameState = getGameState();

            if (gameState == GameState.WIN) {
                clearTerminal();
                System.out.println("Congrats you're a certified asian.\n");
                return;
            }
            if (gameState == GameState.LOSE) {
                clearTerminal();
                System.out.println("Nice try buddy, but you're not him\n");
                System.out.printf("Also the word was: " + this.word);
                return;
            }
            clearTerminal();
            System.out.println("Time remaining: " + remainingTime + " seconds");
            printTheMan();
            guesses.add(getChar());
        }
    }
}
