import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActionChooser {
    public Action choose() throws IOException {
        PersonalBest pb = new PersonalBest();

        System.out.println("HANGMAN:");

        System.out.println("PB: " + pb.get().toString() + "s");

        System.out.println("1. [p]lay");
        System.out.println("2. [w]ords");
        System.out.println("3. [h]ard play");

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
        String input = stdinReader.readLine().trim();

        if (input.isEmpty()) {
            System.out.println("invalid input\n");
            return choose();
        }

        return switch (input.charAt(0)) {
            case 'p' -> Action.PLAY;
            case 'w' -> Action.ADD_WORD;
            case 'h' -> Action.PLAY_HARD;
            default -> {
                System.out.println("don't know what you typed");
                yield choose();
            }
        };
    }
}
