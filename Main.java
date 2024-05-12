import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ActionChooser actionChooser = new ActionChooser();
        Action action = actionChooser.choose();

        switch (action) {
            case PLAY:
                Hangman hangman = new Hangman();
                hangman.init();
                break;

            default:
                break;
        }
    }
}
