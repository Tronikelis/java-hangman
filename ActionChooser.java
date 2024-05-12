import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActionChooser {
    public Action choose() throws IOException {
        System.out.println("HANGMAN:");

        System.out.println("1. [p]lay");
        System.out.println("2. [w]ords");
        System.out.println("3. [l]eaderboard");

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
        String input = stdinReader.readLine().trim();

        if (input.length() == 0) {
            System.out.println("invalid input\n");
            return choose();
        }

        switch (input.charAt(0)) {
            case 'p':
                return Action.PLAY;
            case 'w':
                return Action.ADD_WORD;
            case 'l':
                return Action.SEE_LEADERBOARD;
            default:
                System.out.println("don't know what you typed");
                return choose();
        }
    }
}
